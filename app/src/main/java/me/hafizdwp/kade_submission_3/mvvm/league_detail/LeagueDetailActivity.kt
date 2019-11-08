package me.hafizdwp.kade_submission_3.mvvm.league_detail

import android.content.Context
import androidx.fragment.app.Fragment
import me.hafizdwp.kade_submission_3.R
import me.hafizdwp.kade_submission_3.base.BaseActivity
import me.hafizdwp.kade_submission_3.databinding.LeagueDetailActivityBinding
import me.hafizdwp.kade_submission_3.util.ext.bundleTo
import me.hafizdwp.kade_submission_3.util.ext.startActivity

/**
 * @author hafizdwp
 * 30/10/2019
 **/
class LeagueDetailActivity : BaseActivity<LeagueDetailActivityBinding>() {

    companion object {
        const val EXTRA_LEAGUE_ID = "extra_league_id"

        fun startActivity(context: Context,
                          leagueId: Int) {
            context.startActivity<LeagueDetailActivity>(
                    EXTRA_LEAGUE_ID bundleTo leagueId
            )
        }
    }

    override val bindLayoutRes: Int
        get() = R.layout.league_detail_activity
    override val bindFragmentContainerId: Int?
        get() = mActivityBinding.frameLayout.id
    override val bindFragmentInstance: Fragment?
        get() = LeagueDetailFragment.newInstance()

    var leagueId: Int = 0


    override fun onGetExtras() {
        leagueId = intent.getIntExtra(EXTRA_LEAGUE_ID, 0)
    }
}