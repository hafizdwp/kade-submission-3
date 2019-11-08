package me.hafizdwp.kade_submission_3.data.source.remote

import me.hafizdwp.kade_submission_3.MyApp
import me.hafizdwp.kade_submission_3.R
import me.hafizdwp.kade_submission_3.base.BaseApiModel
import me.hafizdwp.kade_submission_3.data.source.MyDataSource
import me.hafizdwp.kade_submission_3.data.source.remote.api.SportApi
import me.hafizdwp.kade_submission_3.data.source.remote.model.MatchResponse
import me.hafizdwp.kade_submission_3.data.source.remote.model.LeagueDetailResponse
import me.hafizdwp.kade_submission_3.data.source.remote.model.TeamResponse
import me.hafizdwp.kade_submission_3.util.ext.fromJson
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @author hafizdwp
 * 10/07/19
 **/
object MyRemoteDataSource : MyDataSource {

    private val context by lazy { MyApp.getContext() }
    private val sportApi by lazy { ApiServiceFactory.builder<SportApi>() }

    override suspend fun getLeagueDetail(id: Int): BaseApiModel<List<LeagueDetailResponse>> {
        return sportApi.getLeagueDetail(id)
    }

    override suspend fun getLastMatchData(leagueId: Int): BaseApiModel<List<MatchResponse>> {
        return sportApi.getLastMatchOfLeague(leagueId)
    }

    override suspend fun getNextMatchData(leagueId: Int): BaseApiModel<List<MatchResponse>> {
        return sportApi.getNextMatchOfLeague(leagueId)
    }

    override suspend fun getTeamDetail(teamId: Int): BaseApiModel<List<TeamResponse>> {
        return sportApi.getTeamDetail(teamId)
    }

    override suspend fun getMatchesByKeyword(keyword: String): BaseApiModel<List<MatchResponse>> {
        return sportApi.getMatchesByKeyword(keyword)
    }

    override suspend fun getMatchDetail(eventId: Int): BaseApiModel<List<MatchResponse>> {
        return sportApi.getMatchDetail(eventId)
    }

    //    override suspend fun getOnAirTvShows(): BaseApiModel<List<TvShowResponse>> {
//        return try {
//            tvShowApiCour.getOnAirTvShows()
//        } catch (e: Throwable) {
//            e.getError()
//        }
//    }


    ///
    /// Api extentions
    ///

    fun <T> Throwable.getError(): BaseApiModel<T> {
        when (this) {
            is UnknownHostException ->
                return BaseApiModel(
                        status_code = -1,
                        status_message = context.getString(R.string.error_msg_no_internet))

            is SocketTimeoutException ->
                return BaseApiModel(
                        status_code = -1,
                        status_message = context.getString(R.string.error_msg_disconnected))

            is HttpException -> {

                val code = this.code()
                var msg = this.message()
                val errorDao: BaseApiModel<T>?

                try {
                    val body = this.response()?.errorBody()
                    errorDao = body?.string()?.fromJson()

                } catch (exception: Exception) {

                    // When extracting the body failed,
                    // return with throwable message instead
                    return BaseApiModel(
                            status_code = -1,
                            status_message = exception.message)
                }

                when (code) {
                    500 -> msg = errorDao?.status_message
                            ?: context.getString(R.string.error_msg_internal_server_error)
                    503 -> msg = errorDao?.status_message
                            ?: context.getString(R.string.error_msg_server_error)
                    504 -> msg = errorDao?.status_message
                            ?: context.getString(R.string.error_msg_response)
                    502, 404 ->
                        msg = errorDao?.status_message
                                ?: context.getString(R.string.error_msg_error_connect_or_not_found)
                    400 -> msg = errorDao?.status_message
                            ?: context.getString(R.string.error_msg_bad_request)
                    401 -> msg = errorDao?.status_message
                            ?: context.getString(R.string.error_msg_not_authorized)
                }

                // Return with code and filtered msg
                return BaseApiModel(
                        status_code = -1,
                        status_message = msg)
            }

            else ->
                return BaseApiModel(
                        status_code = -1,
                        status_message = this.message)
        }
    }
}