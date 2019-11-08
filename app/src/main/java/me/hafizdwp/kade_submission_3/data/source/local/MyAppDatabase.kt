package me.hafizdwp.kade_submission_3.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.hafizdwp.kade_submission_3.data.Const
import me.hafizdwp.kade_submission_3.data.source.local.dao.ExampleDao
import me.hafizdwp.kade_submission_3.data.source.local.entity.ExampleTable

/**
 * @author hafizdwp
 * 24/07/2019
 **/

@Database(entities = [ExampleTable::class], version = 1)
abstract class MyAppDatabase : RoomDatabase() {

    abstract fun exampleDao(): ExampleDao


    companion object {

        @Volatile
        private var INSTANCE: MyAppDatabase? = null

        fun getInstance(context: Context): MyAppDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also {
                        INSTANCE = it
                    }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(
                        context.applicationContext,
                        MyAppDatabase::class.java,
                        Const.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
    }
}