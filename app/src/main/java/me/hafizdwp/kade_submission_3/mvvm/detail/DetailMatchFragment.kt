package me.hafizdwp.kade_submission_3.mvvm.detail

import android.view.MenuItem
import androidx.lifecycle.LifecycleOwner
import me.hafizdwp.kade_submission_3.R
import me.hafizdwp.kade_submission_3.base.BaseFragment
import me.hafizdwp.kade_submission_3.base.BaseProgressListener
import me.hafizdwp.kade_submission_3.databinding.DetailMatchFragmentBinding
import me.hafizdwp.kade_submission_3.util.ext.obtainViewModel
import me.hafizdwp.kade_submission_3.util.ext.withArgs

/**
 * @author hafizdwp
 * 03/11/2019
 **/
class DetailMatchFragment :
        BaseFragment<DetailMatchActivity, DetailMatchFragmentBinding, DetailMatchViewModel>(), BaseProgressListener {

    companion object {
        fun newInstance() = DetailMatchFragment().withArgs { }
    }

    override val bindLayoutRes: Int
        get() = R.layout.detail_match_fragment
    override val mLifecycleOwner: LifecycleOwner
        get() = this@DetailMatchFragment
    override val mViewModel: DetailMatchViewModel
        get() = obtainViewModel()

    var mAdapter: DetailMatchAdapter? = null


    override fun onSetupBindingVariable(): DetailMatchFragmentBinding {
        setHasOptionsMenu(true)
        return mViewBinding.apply {
            data = null
            homeData = null
            awayData = null
            viewModel = mViewModel
            actionListener = this@DetailMatchFragment
        }
    }

    override fun onViewReady() {
        mViewModel.eventId = mActivity.eventId

        mAdapter = DetailMatchAdapter(mViewModel.listOfGoals)
        mViewBinding.recyclerView.buildRecyclerView(
                mAdapter = mAdapter,
                withDivider = false
        )
    }

    override fun start() {
        mViewModel.getMatchDetail()
    }

    override fun setupObserver(): DetailMatchViewModel? = mViewModel.apply {
        listOfGoals.observe {
            mAdapter?.notifyDataSetChanged()
        }

        detailMatchData.observe {
            mViewBinding.data = it
        }

        homeTeamData.observe {
            mViewBinding.homeData = it
        }

        awayTeamData.observe {
            mViewBinding.awayData = it
        }
    }

    override fun setupToolbar() {
        mActivity.apply {
            mViewBinding.apply {
                setSupportActionBar(toolbar)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setDisplayShowTitleEnabled(false)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        mActivity.onBackPressed()
        return true
    }

    override fun onFailedRetryClick() {
        start()
    }
}
