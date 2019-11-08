package me.hafizdwp.kade_submission_3.base

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * @author hafizdwp
 * 21/10/2019
 **/
open class BasePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private var fragmentList = arrayListOf<Fragment>()
    private var fragmentTitleList = arrayListOf<String>()

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragmentTitleList[position]
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {
        try {
            super.restoreState(state, loader)
        } catch (e: Exception) {
        }
    }

    fun addFragment(fragment: Fragment, title: String? = null) {
        fragmentList.add(fragment)
        title?.let {
            fragmentTitleList.add(title)
        }
    }
}