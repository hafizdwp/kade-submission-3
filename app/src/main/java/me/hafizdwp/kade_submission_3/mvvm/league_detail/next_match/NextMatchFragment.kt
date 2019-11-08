package me.hafizdwp.kade_submission_3.mvvm.league_detail.next_match

import androidx.lifecycle.LifecycleOwner
import me.hafizdwp.kade_submission_3.R
import me.hafizdwp.kade_submission_3.base.BaseFragment
import me.hafizdwp.kade_submission_3.data.source.remote.model.MatchResponse
import me.hafizdwp.kade_submission_3.databinding.NextMatchFragmentBinding
import me.hafizdwp.kade_submission_3.mvvm.detail.DetailMatchActivity
import me.hafizdwp.kade_submission_3.mvvm.league_detail.LeagueDetailActionListener
import me.hafizdwp.kade_submission_3.mvvm.league_detail.LeagueDetailActivity
import me.hafizdwp.kade_submission_3.util.ext.obtainViewModel
import me.hafizdwp.kade_submission_3.util.ext.withArgs

/**
 * @author hafizdwp
 * 30/10/2019
 **/
class NextMatchFragment : BaseFragment<LeagueDetailActivity, NextMatchFragmentBinding, NextMatchViewModel>(), LeagueDetailActionListener {

    companion object {
        fun newInstance() = NextMatchFragment().withArgs { }
    }

    override val bindLayoutRes: Int
        get() = R.layout.next_match_fragment
    override val mLifecycleOwner: LifecycleOwner
        get() = this@NextMatchFragment
    override val mViewModel: NextMatchViewModel
        get() = obtainViewModel()

    var mAdapter: NextMatchAdapter? = null


    override fun onSetupBindingVariable(): NextMatchFragmentBinding {
        return mViewBinding.apply {
            viewModel = mViewModel
            actionListener = this@NextMatchFragment
        }
    }

    override fun onViewReady() {
        mViewModel.leagueId = mActivity.leagueId

        mAdapter = NextMatchAdapter(
                items = mViewModel.nextMatchList,
                listener = this@NextMatchFragment)

        mViewBinding.recyclerView.buildRecyclerView(
                mAdapter = mAdapter,
                withDivider = false
        )
    }

    override fun start() {
        mViewModel.getNextMatchData()
    }

    override fun setupObserver(): NextMatchViewModel? = mViewModel.apply {
        nextMatchList.observe {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun onMatchClick(data: MatchResponse) {
        DetailMatchActivity.startActivity(
                context = mContext,
                eventId = (data.idEvent ?: "0").toInt(),
                matchData = data)
    }

    override fun onFailedRetryClick() {
        start()
    }
}