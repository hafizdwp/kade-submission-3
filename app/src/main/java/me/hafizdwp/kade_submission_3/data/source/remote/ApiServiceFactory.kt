package me.hafizdwp.kade_submission_3.data.source.remote

import com.readystatesoftware.chuck.ChuckInterceptor
import me.hafizdwp.kade_submission_3.BuildConfig
import me.hafizdwp.kade_submission_3.MyApp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author hafizdwp
 * 10/07/19
 **/
object ApiServiceFactory {

    val mClient: OkHttpClient.Builder = OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(ChuckInterceptor(MyApp.getContext()))
                }
            }
            .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
            )
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)

    inline fun <reified T> builder(baseUrl: String = BuildConfig.BASE_URL): T =
            Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(mClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(T::class.java)
}