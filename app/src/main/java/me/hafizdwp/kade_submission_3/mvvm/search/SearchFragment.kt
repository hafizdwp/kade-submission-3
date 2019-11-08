package me.hafizdwp.kade_submission_3.mvvm.search

import android.view.MenuItem
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.delay
import me.hafizdwp.kade_submission_3.R
import me.hafizdwp.kade_submission_3.base.BaseFragment
import me.hafizdwp.kade_submission_3.data.source.remote.model.MatchResponse
import me.hafizdwp.kade_submission_3.databinding.SearchFragmentBinding
import me.hafizdwp.kade_submission_3.mvvm.MainActivity
import me.hafizdwp.kade_submission_3.mvvm.detail.DetailMatchActivity
import me.hafizdwp.kade_submission_3.util.ext.obtainViewModel
import me.hafizdwp.kade_submission_3.util.ext.withArgs

/**
 * @author hafizdwp
 * 07/11/2019
 **/
class SearchFragment : BaseFragment<MainActivity, SearchFragmentBinding, SearchViewModel>(), SearchActionListener {

    companion object {
        fun newInstance() = SearchFragment().withArgs { }
    }

    override val bindLayoutRes: Int
        get() = R.layout.search_fragment
    override val mLifecycleOwner: LifecycleOwner
        get() = this@SearchFragment
    override val mViewModel: SearchViewModel
        get() = obtainViewModel()

    var mAdapter: SearchAdapter? = null


    override fun onSetupBindingVariable(): SearchFragmentBinding {
        setHasOptionsMenu(true)
        return mViewBinding.apply {
            viewModel = mViewModel
            actionListener = this@SearchFragment
        }
    }

    override fun onViewReady() {

        mAdapter = SearchAdapter(
                items = mViewModel.matchesData,
                listener = this)

        mViewBinding.recyclerView.buildRecyclerView(
                mAdapter = mAdapter,
                withDivider = false
        )

        mViewBinding.etSearch.doOnTextChanged { text, start, count, after ->
            mViewModel.apply {
                searchJob?.cancel()
                searchJob = null
                searchJob = launch {
                    delay(SEARCH_DELAY)

                    matchesData.clear()
                    getMatchesByKeyword()
                }
            }
        }
    }

    override fun start() {

    }

    override fun setupObserver(): SearchViewModel? = mViewModel.apply {
        matchesData.observe {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun setupToolbar() {
        mActivity.apply {
            mViewBinding.apply {
                setSupportActionBar(toolbarNew)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setDisplayShowTitleEnabled(false)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        mActivity.onBackPressed()
        return true
    }

    /**
     * SearchActionListener implementations
     * ---------------------------------------------------------------------------------------------
     * */
    override fun onMatchClick(data: MatchResponse) {
        DetailMatchActivity.startActivity(
                context = mContext,
                eventId = (data.idEvent ?: "0").toInt(),
                matchData = data
        )
    }

    override fun onFailedRetryClick() {
        mViewModel.getMatchesByKeyword()
    }
}