package me.hafizdwp.kade_submission_3.mvvm.detail

import android.app.Application
import androidx.databinding.ObservableField
import me.hafizdwp.kade_submission_3.base.BaseViewModel
import me.hafizdwp.kade_submission_3.data.MyRepository
import me.hafizdwp.kade_submission_3.data.source.remote.model.MatchResponse
import me.hafizdwp.kade_submission_3.data.source.remote.model.TeamResponse
import me.hafizdwp.kade_submission_3.util.binding.MyProgressState
import me.hafizdwp.kade_submission_3.util.livedata.SingleListLiveEvent
import me.hafizdwp.kade_submission_3.util.livedata.SingleLiveEvent

/**
 * @author hafizdwp
 * 03/11/2019
 **/
class DetailMatchViewModel(val app: Application,
                           val mRepository: MyRepository) : BaseViewModel(app) {

    val bProgressState = ObservableField<MyProgressState>()

    var eventId = 0

    val listOfGoals = SingleListLiveEvent<DetailMatchGoals>()
    var detailMatchData = SingleLiveEvent<MatchResponse>()
    val homeTeamData = SingleLiveEvent<TeamResponse>()
    val awayTeamData = SingleLiveEvent<TeamResponse>()


    fun getMatchDetail() = launch {

        bProgressState.loading()
        try {
            val response = asyncAwait { mRepository.getMatchDetail(eventId) }
            if (!response.events.isNullOrEmpty()) {
                bProgressState.success()

                detailMatchData.value = response.events?.get(0)
                filterGoals()
                getHomeTeamDetail()
                getAwayTeamDetail()
            } else {
                bProgressState.failed("Error")
            }
        } catch (e: Exception) {
            bProgressState.failed(e.getErrorMessage())
        }
    }

    private fun getHomeTeamDetail() = launch {
        try {
            val homeTeamId = (detailMatchData.value?.idHomeTeam ?: "0").toInt()
            val response = asyncAwait { mRepository.getTeamDetail(homeTeamId) }
            homeTeamData.value = response.teams?.get(0)
        } catch (e: Exception) {
        }
    }

    private fun getAwayTeamDetail() = launch {
        try {
            val awayTeamId = (detailMatchData.value?.idAwayTeam ?: "0").toInt()
            val response = asyncAwait { mRepository.getTeamDetail(awayTeamId) }
            awayTeamData.value = response.teams?.get(0)
        } catch (e: Exception) {
        }
    }

    private fun filterGoals() {
        detailMatchData.value?.let {

            val array = arrayListOf<DetailMatchGoals>()

            val homeGoals = it.strHomeGoalDetails?.split(";")?.toMutableList()
            val awayGoals = it.strAwayGoalDetails?.split(";")?.toMutableList()
            homeGoals?.remove("")
            awayGoals?.remove("")

            homeGoals?.forEach { goal ->
                val minute = goal.substringBefore("'")
                val goalScorer = goal.substringAfter(":")

                array.add(DetailMatchGoals(minute, goalScorer, type = DetailMatchGoals.Type.HOME))
            }
            awayGoals?.forEach { goal ->
                val minute = goal.substringBefore("'")
                val goalScorer = goal.substringAfter(":")

                array.add(DetailMatchGoals(minute, goalScorer, type = DetailMatchGoals.Type.AWAY))
            }

            // sort
            array.sortBy { it.minute }

            // send sorted array
            listOfGoals.clear()
            listOfGoals.addAll(array)
        }
    }
}
