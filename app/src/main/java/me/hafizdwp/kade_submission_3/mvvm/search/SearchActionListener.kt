package me.hafizdwp.kade_submission_3.mvvm.search

import me.hafizdwp.kade_submission_3.base.BaseProgressListener
import me.hafizdwp.kade_submission_3.data.source.remote.model.MatchResponse

/**
 * @author hafizdwp
 * 07/11/2019
 **/
interface SearchActionListener : BaseProgressListener {
    fun onMatchClick(data: MatchResponse)
}