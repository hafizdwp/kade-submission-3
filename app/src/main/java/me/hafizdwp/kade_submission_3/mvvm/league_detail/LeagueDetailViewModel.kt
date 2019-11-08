package me.hafizdwp.kade_submission_3.mvvm.league_detail

import android.app.Application
import androidx.databinding.ObservableField
import me.hafizdwp.kade_submission_3.base.BaseViewModel
import me.hafizdwp.kade_submission_3.data.MyRepository
import me.hafizdwp.kade_submission_3.data.source.remote.model.LeagueDetailResponse
import me.hafizdwp.kade_submission_3.util.binding.MyProgressState
import me.hafizdwp.kade_submission_3.util.livedata.SingleLiveEvent

class LeagueDetailViewModel(val app: Application,
                            val mRepository: MyRepository) : BaseViewModel(app) {

    val bProgressState = ObservableField<MyProgressState>()

    val responseData = SingleLiveEvent<LeagueDetailResponse>()
    var leagueId = 0


    fun getLeagueDetail() = launch {

        bProgressState.loading()

        try {
            val response = asyncAwait { mRepository.getLeagueDetail(leagueId) }
            bProgressState.success()
            responseData.value = response.leagues?.get(0)
        } catch (e: Exception) {
            bProgressState.failed(e.getErrorMessage())
        }
    }

}
