package me.hafizdwp.kade_submission_3.mvvm.league_detail

import me.hafizdwp.kade_submission_3.base.BaseProgressListener
import me.hafizdwp.kade_submission_3.data.source.remote.model.MatchResponse

/**
 * @author hafizdwp
 * 06/11/2019
 **/
interface LeagueDetailActionListener : BaseProgressListener {
    fun onMatchClick(data: MatchResponse)
}