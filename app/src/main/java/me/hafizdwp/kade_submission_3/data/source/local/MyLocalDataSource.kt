package me.hafizdwp.kade_submission_3.data.source.local

import android.content.Context
import me.hafizdwp.kade_submission_3.data.source.MyDataSource
import me.hafizdwp.kade_submission_3.data.source.local.dao.ExampleDao
import me.hafizdwp.kade_submission_3.util.dbhelper.AppExecutors

/**
 * @author hafizdwp
 * 24/07/2019
 **/
class MyLocalDataSource private constructor(
        val context: Context,
        val appExecutors: AppExecutors,
        val exampleDao: ExampleDao) : MyDataSource {

//    override fun getMovieFromFavorite(movieId: Int, callback: MyResponseCallback<ExampleTable>) {
//        appExecutors.diskIO.execute {
//            val data = exampleDao.getFavoritedMovieById(movieId)
//
//            appExecutors.mainThread.execute {
//                if (data != null)
//                    callback.onDataAvailable(data)
//                else
//                    callback.onDataNotAvailable()
//            }
//        }
//    }


    companion object {

        private var INSTANCE: MyLocalDataSource? = null

        @JvmStatic
        fun getInstance(
                context: Context,
                appExecutors: AppExecutors,
                exampleDao: ExampleDao
        ): MyLocalDataSource {
            if (INSTANCE == null) {
                synchronized(MyLocalDataSource::javaClass) {
                    INSTANCE = MyLocalDataSource(context, appExecutors, exampleDao)
                }
            }
            return INSTANCE!!
        }
    }
}