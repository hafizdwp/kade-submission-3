package me.hafizdwp.kade_submission_3.mvvm.detail

import android.content.Context
import androidx.fragment.app.Fragment
import me.hafizdwp.kade_submission_3.R
import me.hafizdwp.kade_submission_3.base.BaseActivity
import me.hafizdwp.kade_submission_3.data.source.remote.model.MatchResponse
import me.hafizdwp.kade_submission_3.databinding.DetailMatchActivityBinding
import me.hafizdwp.kade_submission_3.util.ext.bundleTo
import me.hafizdwp.kade_submission_3.util.ext.startActivity

/**
 * @author hafizdwp
 * 03/11/2019
 **/
class DetailMatchActivity : BaseActivity<DetailMatchActivityBinding>() {

    companion object {
        const val EXTRA_EVENT_ID = "extra"
//        const val EXTRA_MATCH_DATA = "extra_match"

        fun startActivity(context: Context, eventId: Int,
                          matchData: MatchResponse? = null) {
            context.startActivity<DetailMatchActivity>(
                    EXTRA_EVENT_ID bundleTo eventId
//                    EXTRA_MATCH_DATA bundleTo matchData
            )
        }
    }

    override val bindLayoutRes: Int
        get() = R.layout.detail_match_activity
    override val bindFragmentContainerId: Int?
        get() = mActivityBinding.frameLayout.id
    override val bindFragmentInstance: Fragment?
        get() = DetailMatchFragment.newInstance()

    var eventId = 0
//    var matchData: MatchResponse? = null


    override fun onGetExtras() {
        eventId = intent.getIntExtra(EXTRA_EVENT_ID, 0)
//        matchData = intent.getParcelableExtra(EXTRA_MATCH_DATA)
    }
}