package me.hafizdwp.kade_submission_3.base

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import me.hafizdwp.kade_submission_3.R
import me.hafizdwp.kade_submission_3.util.binding.MyProgressState
import me.hafizdwp.kade_submission_3.util.livedata.SingleLiveEvent
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext

/**
 * @author hafizdwp
 * 29/10/2019
 **/
abstract class BaseViewModel(private val app: Application) : AndroidViewModel(app), CoroutineScope {

    val viewModelJob = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + viewModelJob


    val globalToast = SingleLiveEvent<String>()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    fun launch(action: suspend CoroutineScope.() -> Unit):
            Job = CoroutineScope(coroutineContext).launch { action(this) }

    suspend fun <T> asyncAwait(context: CoroutineContext = Dispatchers.IO,
                               action: suspend CoroutineScope.() -> T): T =
            CoroutineScope(context).async { action(this) }.await()

    fun ObservableField<MyProgressState>.loading() = set(MyProgressState.Loading)
    fun ObservableField<MyProgressState>.success() = set(MyProgressState.Success)
    fun ObservableField<MyProgressState>.empty(emptyMsg: String?) = set(MyProgressState.Empty(emptyMsg))
    fun ObservableField<MyProgressState>.failed(errorMsg: String?) = set(MyProgressState.Failed(errorMsg))

    fun Throwable.getErrorMessage(): String {
        var errorMsg = ""
        when (this) {
            is HttpException -> {
                errorMsg = when (this.code()) {
                    -100, -200 ->
                        app.getString(R.string.error_message_no_internet_connection)

                    500, 502, 504 ->
                        app.getString(R.string.error_message_server)

                    429,
                    404 ->
                        "Terjadi kesalahan dengan kode ${this.code()}"

                    else ->
                        app.getString(R.string.error_message_unexpected)
                }
            }
            is UnknownHostException ->
                errorMsg = app.getString(R.string.error_message_no_internet_connection)
            is SocketTimeoutException ->
                errorMsg = app.getString(R.string.error_message_no_internet_connection)
            else ->
                errorMsg = app.getString(R.string.error_message_unexpected)
        }

        return errorMsg
    }
}