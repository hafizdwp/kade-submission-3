package me.hafizdwp.kade_submission_3.util

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.hafizdwp.kade_submission_3.data.MyRepository
import me.hafizdwp.kade_submission_3.data.source.local.MyAppDatabase
import me.hafizdwp.kade_submission_3.data.source.local.MyLocalDataSource
import me.hafizdwp.kade_submission_3.data.source.remote.MyRemoteDataSource
import me.hafizdwp.kade_submission_3.mvvm.MainViewModel
import me.hafizdwp.kade_submission_3.mvvm.detail.DetailMatchViewModel
import me.hafizdwp.kade_submission_3.mvvm.league.LeagueViewModel
import me.hafizdwp.kade_submission_3.mvvm.league_detail.LeagueDetailViewModel
import me.hafizdwp.kade_submission_3.mvvm.league_detail.next_match.NextMatchViewModel
import me.hafizdwp.kade_submission_3.mvvm.league_detail.last_match.LastMatchViewModel
import me.hafizdwp.kade_submission_3.mvvm.match.MatchViewModel
import me.hafizdwp.kade_submission_3.mvvm.search.SearchViewModel
import me.hafizdwp.kade_submission_3.util.dbhelper.AppExecutors

/**
 * Created by irfanirawansukirman on 26/01/18.
 */

class ViewModelFactory private constructor(
        private val mApplication: Application,
        private val mRepository: MyRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
            with(modelClass) {
                when {

                    isAssignableFrom(MainViewModel::class.java) ->
                        MainViewModel(mApplication, mRepository)
                    isAssignableFrom(MatchViewModel::class.java) ->
                        MatchViewModel(mApplication, mRepository)
                    isAssignableFrom(LeagueViewModel::class.java) ->
                        LeagueViewModel(mApplication, mRepository)
                    isAssignableFrom(LeagueDetailViewModel::class.java) ->
                        LeagueDetailViewModel(mApplication, mRepository)
                    isAssignableFrom(NextMatchViewModel::class.java) ->
                        NextMatchViewModel(mApplication, mRepository)
                    isAssignableFrom(LastMatchViewModel::class.java) ->
                        LastMatchViewModel(mApplication, mRepository)
                    isAssignableFrom(DetailMatchViewModel::class.java) ->
                        DetailMatchViewModel(mApplication, mRepository)
                    isAssignableFrom(SearchViewModel::class.java) ->
                        SearchViewModel(mApplication, mRepository)

                    else ->
                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application) =
                INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                    INSTANCE ?: ViewModelFactory(
                            mApplication = application,
                            mRepository = provideRepository(application.applicationContext))
                            .also { INSTANCE = it }
                }

        @JvmStatic
        fun provideRepository(context: Context): MyRepository {
            val localDatabase = MyAppDatabase.getInstance(context)

            return MyRepository.getInstance(
                    MyRemoteDataSource,
                    MyLocalDataSource.getInstance(
                            context,
                            AppExecutors(),
                            localDatabase.exampleDao()
                    )
            )
        }
    }
}