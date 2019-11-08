package me.hafizdwp.kade_submission_3.mvvm.league_detail.last_match

import androidx.lifecycle.LifecycleOwner
import me.hafizdwp.kade_submission_3.R
import me.hafizdwp.kade_submission_3.base.BaseFragment
import me.hafizdwp.kade_submission_3.data.source.remote.model.MatchResponse
import me.hafizdwp.kade_submission_3.databinding.LastMatchFragmentBinding
import me.hafizdwp.kade_submission_3.mvvm.detail.DetailMatchActivity
import me.hafizdwp.kade_submission_3.mvvm.league_detail.LeagueDetailActionListener
import me.hafizdwp.kade_submission_3.mvvm.league_detail.LeagueDetailActivity
import me.hafizdwp.kade_submission_3.util.ext.obtainViewModel
import me.hafizdwp.kade_submission_3.util.ext.withArgs

/**
 * @author hafizdwp
 * 30/10/2019
 **/
class LastMatchFragment : BaseFragment<LeagueDetailActivity, LastMatchFragmentBinding, LastMatchViewModel>(), LeagueDetailActionListener {

    companion object {
        fun newInstance() = LastMatchFragment().withArgs { }
    }

    override val bindLayoutRes: Int
        get() = R.layout.last_match_fragment
    override val mLifecycleOwner: LifecycleOwner
        get() = this@LastMatchFragment
    override val mViewModel: LastMatchViewModel
        get() = obtainViewModel()

    var mAdapter: LastMatchAdapter? = null


    override fun onSetupBindingVariable(): LastMatchFragmentBinding {
        return mViewBinding.apply {
            viewModel = mViewModel
            actionListener = this@LastMatchFragment
        }
    }

    override fun onViewReady() {
        mViewModel.leagueId = mActivity.leagueId

        mAdapter = LastMatchAdapter(
                items = mViewModel.lastMatchList,
                listener = this@LastMatchFragment)

        mViewBinding.recyclerView.buildRecyclerView(
                mAdapter = mAdapter,
                withDivider = false
        )
    }

    override fun start() {
        mViewModel.getLastMatchData()
    }

    override fun setupObserver(): LastMatchViewModel? = mViewModel.apply {
        lastMatchList.observe {
            mAdapter?.notifyDataSetChanged()
        }
    }


    /**
     * LeagueDetailActionListener implementations
     * ---------------------------------------------------------------------------------------------
     * */
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