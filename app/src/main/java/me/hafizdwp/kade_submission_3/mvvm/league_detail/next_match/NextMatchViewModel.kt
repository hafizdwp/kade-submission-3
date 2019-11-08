package me.hafizdwp.kade_submission_3.mvvm.league_detail.next_match

import android.app.Application
import androidx.databinding.ObservableField
import me.hafizdwp.kade_submission_3.base.BaseViewModel
import me.hafizdwp.kade_submission_3.data.MyRepository
import me.hafizdwp.kade_submission_3.data.source.remote.model.MatchResponse
import me.hafizdwp.kade_submission_3.util.binding.MyProgressState
import me.hafizdwp.kade_submission_3.util.livedata.SingleListLiveEvent

class NextMatchViewModel(val app: Application,
                         val mRepository: MyRepository) : BaseViewModel(app) {


    val bProgressState = ObservableField<MyProgressState>()

    val nextMatchList = SingleListLiveEvent<MatchResponse>()
    var leagueId = 0


    fun getNextMatchData() = launch {

        bProgressState.loading()
        try {
            val response = asyncAwait { mRepository.getNextMatchData(leagueId) }
            bProgressState.success()
            nextMatchList.clear()
            nextMatchList.addAll(response.events ?: emptyList())
        } catch (e: Exception) {
            bProgressState.failed(e.getErrorMessage())
        }
    }
}
