package me.hafizdwp.kade_submission_3.mvvm.search

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Job
import me.hafizdwp.kade_submission_3.R
import me.hafizdwp.kade_submission_3.base.BaseViewModel
import me.hafizdwp.kade_submission_3.data.MyRepository
import me.hafizdwp.kade_submission_3.data.source.remote.model.MatchResponse
import me.hafizdwp.kade_submission_3.util.binding.MyProgressState
import me.hafizdwp.kade_submission_3.util.livedata.SingleListLiveEvent

/**
 * @author hafizdwp
 * 07/11/2019
 **/
class SearchViewModel(val app: Application,
                      val mRepository: MyRepository) : BaseViewModel(app) {

    val bProgressState = ObservableField<MyProgressState>()
    val bKeyword = MutableLiveData<String>()

    val matchesData = SingleListLiveEvent<MatchResponse>()

    val SEARCH_DELAY = 500L
    var searchJob: Job? = null


    fun getMatchesByKeyword(keyword: String? = bKeyword.value) = doIfKeywordValid(keyword) {
        launch {

            bProgressState.loading()
            try {
                val response = asyncAwait { mRepository.getMatchesByKeyword(keyword!!) }
                if (response.event.isNullOrEmpty())
                    bProgressState.empty(app.getString(R.string.error_message_no_content))
                else {
                    bProgressState.success()

                    matchesData.clear()
                    response.event!!.forEach {
                        if ((it.takeIf {
                                    it.strSport == "Soccer" && (it.dateEvent?.substring(0, 4) ?: "0").toInt() < 2020
                                }) != null) {
                            matchesData.add(it)
                        }
                    }
                }
            } catch (e: Exception) {
                bProgressState.failed(e.getErrorMessage())
            }
        }
    }

    private fun doIfKeywordValid(keyword: String?,
                                 action: () -> Unit) {
        if (!keyword.isNullOrBlank() && keyword.length > 2)
            action.invoke()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        searchJob?.cancel()
    }
}
