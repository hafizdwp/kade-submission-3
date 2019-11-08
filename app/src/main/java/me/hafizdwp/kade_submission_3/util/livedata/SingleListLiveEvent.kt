package me.hafizdwp.kade_submission_3.util.livedata

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author hafizdwp
 * 29/10/2019
 **/
class SingleListLiveEvent<T> : MutableListLiveData<T>() {

    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in MutableList<T>>) {
        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.")
        }

        // Observe the internal MutableLiveData
        super.observe(owner, Observer<MutableList<T>> {
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(it)
            }
        })
    }

    @MainThread
    override fun setValue(value: MutableList<T>?) {
        pending.set(true)
        super.setValue(value)
    }

    @MainThread
    fun call() {
        value = mutableListOf()
    }

    companion object {
        private const val TAG = "SingleListLiveEvent"
    }
}