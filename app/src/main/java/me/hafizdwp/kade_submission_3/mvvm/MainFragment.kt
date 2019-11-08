package me.hafizdwp.kade_submission_3.mvvm

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import me.hafizdwp.kade_submission_3.R
import me.hafizdwp.kade_submission_3.base.BaseFragment
import me.hafizdwp.kade_submission_3.databinding.MainFragmentBinding
import me.hafizdwp.kade_submission_3.mvvm.league.LeagueFragment
import me.hafizdwp.kade_submission_3.mvvm.search.SearchFragment
import me.hafizdwp.kade_submission_3.util.ext.log
import me.hafizdwp.kade_submission_3.util.ext.obtainViewModel
import me.hafizdwp.kade_submission_3.util.ext.withArgs

/**
 * @author hafizdwp
 * 07/11/2019
 **/
class MainFragment : BaseFragment<MainActivity, MainFragmentBinding, MainViewModel>() {

    companion object {
        fun newInstance() = MainFragment().withArgs { }
    }

    override val bindLayoutRes: Int
        get() = R.layout.main_fragment
    override val mLifecycleOwner: LifecycleOwner
        get() = this@MainFragment
    override val mViewModel: MainViewModel
        get() = obtainViewModel()

    val mFragmentList = ArrayList<Fragment>()


    override fun onSetupBindingVariable(): MainFragmentBinding {
        return mViewBinding
    }

    override fun onViewReady() {

        childFragmentManager.fragments.forEach {
            childFragmentManager.beginTransaction().remove(it).commit()
            log(it.toString() + "removed")
        }

        mFragmentList.clear()
        mFragmentList.apply {
            //add(MatchFragment.newInstance())
            add(LeagueFragment.newInstance())
        }
    }

    override fun start() {

    }

    override fun setupToolbar() {
        mActivity.apply {
            mViewBinding.apply {
                setSupportActionBar(toolbar)
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.setDisplayShowTitleEnabled(false)
            }
        }

        // Search icon function
        mViewBinding.imgToolbarSearch.setOnClickListener {
            mActivity.addFragmentWithBackstack(SearchFragment.newInstance())
        }
    }

    override fun setupObserver(): MainViewModel? = mViewModel.apply {
        bnavPosition.observe {
            changeFragments(mFragmentList[it ?: 0])
        }
    }

    fun changeFragments(fragment: Fragment) {
        childFragmentManager.beginTransaction().apply {

            // Add fragment if one isn't before
            if (!fragment.isAdded)
                add(mViewBinding.frameLayout.id, fragment)

            // Hide every fragment that was added
            childFragmentManager.fragments.forEach {
                hide(it)
            }

            // Show and go
            show(fragment)
            commit()
        }
    }
}