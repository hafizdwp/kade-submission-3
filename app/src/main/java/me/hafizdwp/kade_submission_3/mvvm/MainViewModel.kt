package me.hafizdwp.kade_submission_3.mvvm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import me.hafizdwp.kade_submission_3.base.BaseViewModel
import me.hafizdwp.kade_submission_3.data.MyRepository

/**
 * @author hafizdwp
 * 30/10/2019
 **/
class MainViewModel(application: Application,
                    val mRepository: MyRepository) : BaseViewModel(application) {

    val bnavPosition = MutableLiveData<Int>().apply {
        value = 0
    }
}