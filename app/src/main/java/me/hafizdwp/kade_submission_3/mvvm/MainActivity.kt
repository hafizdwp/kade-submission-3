package me.hafizdwp.kade_submission_3.mvvm

import android.content.Context
import android.os.Handler
import androidx.fragment.app.Fragment
import me.hafizdwp.kade_submission_3.R
import me.hafizdwp.kade_submission_3.base.BaseActivity
import me.hafizdwp.kade_submission_3.databinding.MainActivityBinding
import me.hafizdwp.kade_submission_3.util.ext.startActivity
import me.hafizdwp.kade_submission_3.util.ext.toast

class MainActivity : BaseActivity<MainActivityBinding>() {

    companion object {
        fun startActivity(context: Context) {
            context.startActivity<MainActivity>()
        }
    }

    override val bindLayoutRes: Int
        get() = R.layout.main_activity
    override val bindFragmentContainerId: Int?
        get() = mActivityBinding.frameLayout.id
    override val bindFragmentInstance: Fragment?
        get() = MainFragment.newInstance()

    /// Double-backpress-to-exit-mechanism
    var doubleBackPressToExit = false
    var handlerBack: Handler? = Handler()
    var runnableBack = Runnable { doubleBackPressToExit = false }


    override fun onGetExtras() {

    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount != 0)
            supportFragmentManager.popBackStackImmediate()
        else {
            if (doubleBackPressToExit) {
                supportFinishAfterTransition()
                return
            }

            toast(getString(R.string.double_back_to_exit))
            doubleBackPressToExit = true
            handlerBack?.postDelayed(runnableBack, 2000)
        }
    }

    /// BottomNav
//    val mBottomNavItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
//        when (menuItem.itemId) {
////            R.id.bnav_matches -> {
////                mViewModel.bnavPosition.value = 0
////                return@OnNavigationItemSelectedListener true
////            }
//            R.id.bnav_league -> {
//                mViewModel.bnavPosition.value = 1
//                return@OnNavigationItemSelectedListener true
//            }
//            else -> {
//                return@OnNavigationItemSelectedListener false
//            }
//        }
//    }

//    fun setupObserver() {
//        mViewModel.apply {
//            bnavPosition.observe(this@MainActivity, Observer {
//                changeFragments(mFragmentList[it])
//            })
//        }
//    }

//    fun changeFragments(fragment: Fragment) {
//        supportFragmentManager.beginTransaction().apply {
//
//            // Add fragment if one isn't before
//            if (!fragment.isAdded)
//                add(mActivityBinding.frameLayout.id, fragment)
//
//            // Hide every fragment that was added
//            supportFragmentManager.fragments.forEach {
//                hide(it)
//            }
//
//            // Show and go
//            show(fragment)
//            commit()
//        }
//    }
}
