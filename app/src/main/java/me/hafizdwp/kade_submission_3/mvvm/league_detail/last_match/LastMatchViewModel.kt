package me.hafizdwp.kade_submission_3.mvvm.league_detail.last_match

import android.app.Application
import androidx.databinding.ObservableField
import me.hafizdwp.kade_submission_3.base.BaseViewModel
import me.hafizdwp.kade_submission_3.data.MyRepository
import me.hafizdwp.kade_submission_3.data.source.remote.model.MatchResponse
import me.hafizdwp.kade_submission_3.util.binding.MyProgressState
import me.hafizdwp.kade_submission_3.util.livedata.SingleListLiveEvent

class LastMatchViewModel(val app: Application,
                         val mRepository: MyRepository) : BaseViewModel(app) {

    val bProgressState = ObservableField<MyProgressState>()

    val lastMatchList = SingleListLiveEvent<MatchResponse>()
    var leagueId = 0


    fun getLastMatchData() = launch {

        bProgressState.loading()
        try {
            val response = asyncAwait { mRepository.getLastMatchData(leagueId) }
            bProgressState.success()
            lastMatchList.clear()
            lastMatchList.addAll(response.events ?: emptyList())
        } catch (e: Exception) {
            bProgressState.failed(e.getErrorMessage())
        }
    }

}
