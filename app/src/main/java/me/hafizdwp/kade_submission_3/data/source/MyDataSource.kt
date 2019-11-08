package me.hafizdwp.kade_submission_3.data.source

import me.hafizdwp.kade_submission_3.base.BaseApiModel
import me.hafizdwp.kade_submission_3.data.source.remote.model.LeagueDetailResponse
import me.hafizdwp.kade_submission_3.data.source.remote.model.MatchResponse
import me.hafizdwp.kade_submission_3.data.source.remote.model.TeamResponse

/**
 * @author hafizdwp
 * 10/07/19
 **/
interface MyDataSource {

    ///
    /// Remote
    ///

    suspend fun getLeagueDetail(id: Int): BaseApiModel<List<LeagueDetailResponse>> {
        throw Exception("remote only")
    }

    suspend fun getLastMatchData(leagueId: Int): BaseApiModel<List<MatchResponse>> {
        throw Exception("remote only")
    }

    suspend fun getNextMatchData(leagueId: Int): BaseApiModel<List<MatchResponse>> {
        throw Exception("remote only")
    }

    suspend fun getTeamDetail(teamId: Int): BaseApiModel<List<TeamResponse>> {
        throw Exception("remote only")
    }

    suspend fun getMatchesByKeyword(keyword: String): BaseApiModel<List<MatchResponse>> {
        throw Exception("remote only")
    }

    suspend fun getMatchDetail(eventId: Int): BaseApiModel<List<MatchResponse>> {
        throw Exception("remote only")
    }

    ///
    /// Local
    ///
}