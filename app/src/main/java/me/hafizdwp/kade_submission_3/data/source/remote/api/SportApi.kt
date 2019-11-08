package me.hafizdwp.kade_submission_3.data.source.remote.api

import me.hafizdwp.kade_submission_3.base.BaseApiModel
import me.hafizdwp.kade_submission_3.data.source.remote.model.LeagueDetailResponse
import me.hafizdwp.kade_submission_3.data.source.remote.model.MatchResponse
import me.hafizdwp.kade_submission_3.data.source.remote.model.TeamResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author hafizdwp
 * 30/10/2019
 **/
interface SportApi {

    @GET("lookupleague.php")
    suspend fun getLeagueDetail(
            @Query("id") leagueId: Int
    ): BaseApiModel<List<LeagueDetailResponse>>

    @GET("eventsnextleague.php")
    suspend fun getNextMatchOfLeague(
            @Query("id") leagueId: Int
    ): BaseApiModel<List<MatchResponse>>

    @GET("eventspastleague.php")
    suspend fun getLastMatchOfLeague(
            @Query("id") leagueId: Int
    ): BaseApiModel<List<MatchResponse>>

    @GET("lookupteam.php?id=133615")
    suspend fun getTeamDetail(
            @Query("id") teamId: Int
    ): BaseApiModel<List<TeamResponse>>

    @GET("searchevents.php")
    suspend fun getMatchesByKeyword(
            @Query("e") keyword: String
    ): BaseApiModel<List<MatchResponse>>

    @GET("lookupevent.php")
    suspend fun getMatchDetail(
            @Query("id") eventId: Int
    ): BaseApiModel<List<MatchResponse>>
}