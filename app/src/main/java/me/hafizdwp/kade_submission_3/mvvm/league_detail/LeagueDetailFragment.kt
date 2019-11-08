package me.hafizdwp.kade_submission_3.mvvm.league_detail

import android.view.MenuItem
import androidx.lifecycle.LifecycleOwner
import me.hafizdwp.kade_submission_3.R
import me.hafizdwp.kade_submission_3.base.BaseFragment
import me.hafizdwp.kade_submission_3.base.BasePagerAdapter
import me.hafizdwp.kade_submission_3.base.BaseProgressListener
import me.hafizdwp.kade_submission_3.databinding.LeagueDetailFragmentBinding
import me.hafizdwp.kade_submission_3.mvvm.league_detail.last_match.LastMatchFragment
import me.hafizdwp.kade_submission_3.mvvm.league_detail.next_match.NextMatchFragment
import me.hafizdwp.kade_submission_3.util.ext.obtainViewModel
import me.hafizdwp.kade_submission_3.util.ext.withArgs

/**
 * @author hafizdwp
 * 30/10/2019
 **/
class LeagueDetailFragment : BaseFragment<LeagueDetailActivity, LeagueDetailFragmentBinding, LeagueDetailViewModel>(), BaseProgressListener {

    companion object {
        fun newInstance() = LeagueDetailFragment().withArgs { }
    }

    override val bindLayoutRes: Int
        get() = R.layout.league_detail_fragment
    override val mLifecycleOwner: LifecycleOwner
        get() = this@LeagueDetailFragment
    override val mViewModel: LeagueDetailViewModel
        get() = obtainViewModel()

    var mAdapter: BasePagerAdapter? = null


    override fun onSetupBindingVariable(): LeagueDetailFragmentBinding {
        setHasOptionsMenu(true)
        return mViewBinding.apply {
            viewModel = mViewModel
            actionListener = this@LeagueDetailFragment
        }
    }

    override fun onViewReady() {
        mViewModel.leagueId = mActivity.leagueId

        // Setup tabLayout & viewPager
        mAdapter = BasePagerAdapter(childFragmentManager)
        mViewBinding.viewPager.adapter = mAdapter

        mAdapter?.addFragment(
                fragment = LastMatchFragment.newInstance(),
                title = getString(R.string.last_match))
        mAdapter?.addFragment(
                fragment = NextMatchFragment.newInstance(),
                title = getString(R.string.next_match))
        mAdapter?.notifyDataSetChanged()

        mViewBinding.tabLayout.setupWithViewPager(mViewBinding.viewPager)
    }

    override fun start() {
        mViewModel.getLeagueDetail()
    }

    override fun setupObserver(): LeagueDetailViewModel? = mViewModel.apply {
        responseData.observe {
            mViewBinding.data = it
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