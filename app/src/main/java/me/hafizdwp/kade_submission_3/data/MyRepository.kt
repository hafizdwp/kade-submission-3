package me.hafizdwp.kade_submission_3.data

import me.hafizdwp.kade_submission_3.base.BaseApiModel
import me.hafizdwp.kade_submission_3.data.source.MyDataSource
import me.hafizdwp.kade_submission_3.data.source.local.MyLocalDataSource
import me.hafizdwp.kade_submission_3.data.source.remote.MyRemoteDataSource
import me.hafizdwp.kade_submission_3.data.source.remote.model.LeagueDetailResponse
import me.hafizdwp.kade_submission_3.data.source.remote.model.MatchResponse
import me.hafizdwp.kade_submission_3.data.source.remote.model.TeamResponse

/**
 * @author hafizdwp
 * 10/07/19
 **/
open class MyRepository(val remoteDataSource: MyRemoteDataSource,
                        val localDataSource: MyLocalDataSource) : MyDataSource {


    override suspend fun getLeagueDetail(id: Int): BaseApiModel<List<LeagueDetailResponse>> {
        return remoteDataSource.getLeagueDetail(id)
    }

    override suspend fun getLastMatchData(leagueId: Int): BaseApiModel<List<MatchResponse>> {
        return remoteDataSource.getLastMatchData(leagueId)
    }

    override suspend fun getNextMatchData(leagueId: Int): BaseApiModel<List<MatchResponse>> {
        return remoteDataSource.getNextMatchData(leagueId)
    }

    override suspend fun getTeamDetail(teamId: Int): BaseApiModel<List<TeamResponse>> {
        return remoteDataSource.getTeamDetail(teamId)
    }

    override suspend fun getMatchesByKeyword(keyword: String): BaseApiModel<List<MatchResponse>> {
        return remoteDataSource.getMatchesByKeyword(keyword)
    }

    override suspend fun getMatchDetail(eventId: Int): BaseApiModel<List<MatchResponse>> {
        return remoteDataSource.getMatchDetail(eventId)
    }

    companion object {
        private var INSTANCE: MyRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.
         */
        @JvmStatic
        fun getInstance(remoteDataSource: MyRemoteDataSource,
                        localDataSource: MyLocalDataSource) =
                INSTANCE
                        ?: synchronized(MyRepository::class.java) {
                            INSTANCE
                                    ?: MyRepository(remoteDataSource, localDataSource)
                                            .also { INSTANCE = it }
                        }
    }
}