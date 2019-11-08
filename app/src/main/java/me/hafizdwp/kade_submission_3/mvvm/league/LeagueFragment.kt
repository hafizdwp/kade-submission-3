package me.hafizdwp.kade_submission_3.mvvm.league

import androidx.lifecycle.LifecycleOwner
import me.hafizdwp.kade_submission_3.R
import me.hafizdwp.kade_submission_3.base.BaseFragment
import me.hafizdwp.kade_submission_3.databinding.LeagueFragmentBinding
import me.hafizdwp.kade_submission_3.mvvm.MainActivity
import me.hafizdwp.kade_submission_3.mvvm.league_detail.LeagueDetailActivity
import me.hafizdwp.kade_submission_3.util.ext.obtainViewModel
import me.hafizdwp.kade_submission_3.util.ext.withArgs

/**
 * @author hafizdwp
 * 30/10/2019
 **/
class LeagueFragment : BaseFragment<MainActivity, LeagueFragmentBinding, LeagueViewModel>() {

    companion object {
        fun newInstance() = LeagueFragment().withArgs { }
    }

    override val bindLayoutRes: Int
        get() = R.layout.league_fragment
    override val mLifecycleOwner: LifecycleOwner
        get() = this@LeagueFragment
    override val mViewModel: LeagueViewModel
        get() = obtainViewModel()

    var mAdapter: LeagueAdapter? = null


    override fun onSetupBindingVariable(): LeagueFragmentBinding {
        return mViewBinding.apply {

        }
    }

    override fun onViewReady() {
        mAdapter = LeagueAdapter(object : LeagueActionListener {
            override fun onLeagueClick(data: LeagueData) {
                LeagueDetailActivity.startActivity(mContext, data.id)
            }
        })

        mViewBinding.recycler.buildRecyclerView(
                mAdapter = mAdapter,
                withDivider = false
        )
    }

    override fun start() {

    }

    override fun setupObserver(): LeagueViewModel? = mViewModel.apply {

    }
}