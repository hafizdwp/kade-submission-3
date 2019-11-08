package me.hafizdwp.kade_submission_3

import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho

/**
 * @author hafizdwp
 * 05/07/19
 **/
class MyApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this

        // Stetho
        Stetho.initializeWithDefaults(this)
    }

    companion object {
        lateinit var instance: MyApp

        fun getContext() = instance.applicationContext
    }
}