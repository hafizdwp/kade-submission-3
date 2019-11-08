package me.hafizdwp.kade_submission_3.mvvm.match

import androidx.lifecycle.LifecycleOwner
import me.hafizdwp.kade_submission_3.R
import me.hafizdwp.kade_submission_3.base.BaseFragment
import me.hafizdwp.kade_submission_3.databinding.MatchFragmentBinding
import me.hafizdwp.kade_submission_3.mvvm.MainActivity
import me.hafizdwp.kade_submission_3.util.ext.obtainViewModel
import me.hafizdwp.kade_submission_3.util.ext.withArgs

/**
 * @author hafizdwp
 * 30/10/2019
 **/
class MatchFragment : BaseFragment<MainActivity, MatchFragmentBinding, MatchViewModel>() {

    companion object {
        fun newInstance() = MatchFragment().withArgs { }
    }

    override val bindLayoutRes: Int
        get() = R.layout.match_fragment
    override val mLifecycleOwner: LifecycleOwner
        get() = this@MatchFragment
    override val mViewModel: MatchViewModel
        get() = obtainViewModel()

    override fun onSetupBindingVariable(): MatchFragmentBinding {
        return mViewBinding.apply {

        }
    }

    override fun onViewReady() {
    }

    override fun start() {
    }

    override fun setupObserver(): MatchViewModel? = mViewModel.apply {
    }
}