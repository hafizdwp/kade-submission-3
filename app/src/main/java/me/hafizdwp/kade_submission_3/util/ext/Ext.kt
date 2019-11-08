package me.hafizdwp.kade_submission_3.util.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.RequestOptions
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import me.hafizdwp.kade_submission_3.util.ViewModelFactory

/**
 * @author hafizdwp
 * 29/10/2019
 **/

inline fun <reified T : ViewModel> AppCompatActivity.obtainViewModel() =
        ViewModelProviders.of(this, ViewModelFactory.getInstance(application)).get(T::class.java)

inline fun <reified VM : ViewModel> Fragment.obtainViewModel() =
        ViewModelProviders.of(requireActivity(),
                ViewModelFactory.getInstance(requireActivity().application)
        ).get(VM::class.java)

/**
 * Log ext
 * */
fun log(msg: String,
        tag: String? = null) {
    Log.d(tag ?: "mytag", msg)
}

fun logError(msg: String,
             tag: String? = null) {
    Log.e(tag ?: "mytag", msg)
}

fun logWarning(msg: String,
               tag: String? = null) {
    Log.w(tag ?: "mytag", msg)
}

/**
 * Ez toast
 * */
var mToast: Toast? = null

fun AppCompatActivity.toast(msg: String) {
    if (msg.isNotBlank()) {
        mToast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
        mToast?.show()
    }
}

fun AppCompatActivity.toastSpammable(msg: String?) {
    if (msg != null) {
        if (msg.isNotBlank()) {
            if (mToast != null) mToast?.cancel()
            mToast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
            mToast?.show()
        }
    }
}

fun Fragment.toast(msg: String) {
    (requireActivity() as? AppCompatActivity)?.toast(msg)
}

fun Fragment.toastSpammable(msg: String?) {
    (requireActivity() as? AppCompatActivity)?.toastSpammable(msg)
}

/**
 * Ez start activity
 * */
inline fun <reified T> Context.startActivity(vararg bundlePair: BundlePair, withFlags: Boolean = false) {
    startActivity(
            Intent(this, T::class.java).apply {
                if (withFlags) {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                putExtras(bundleOf(*bundlePair))
            }
    )
}

/**
 * Fragment ez argument
 * */
fun <T : Fragment> T.withArgs(
        argsBuilder: Bundle.() -> Unit): T =
        this.apply {
            arguments = Bundle().apply(argsBuilder)
        }

fun RecyclerView.dividerItemDecoration(): DividerItemDecoration {
    return DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
}

fun RequestBuilder<Drawable>.requestOptions(listRequestOptions: RequestOptions.() -> RequestOptions): RequestBuilder<Drawable> {
    return this.apply(RequestOptions().listRequestOptions())
}

fun RequestOptions.withLoadingPlaceholder(context: Context): RequestOptions {
    return placeholder(context.circularProgressDrawable())
}

fun <T : Drawable> RequestBuilder<T>.withLoadingPlaceholder(context: Context): RequestBuilder<T> {
    return this.apply(
            RequestOptions().placeholder(context.circularProgressDrawable())
    )
}

fun Context.circularProgressDrawable(): CircularProgressDrawable =
        CircularProgressDrawable(this).apply {
            strokeWidth = 5f
            centerRadius = 30f
            start()
        }

/**
 * View visibility utility
 * */
fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

fun View.isInvisible(): Boolean {
    return visibility == View.INVISIBLE
}

fun View.isGone(): Boolean {
    return visibility == View.GONE
}

/**
 * Gson ez toJson / fromJson
 * ---------------------------------------------------------------------------------------------
 * */
val gson by lazy { GsonBuilder().setPrettyPrinting().create() }

fun <T> T.toJson(): String = gson.toJson(this)
inline fun <reified T> makeType() = object : TypeToken<T>() {}.type
inline fun <reified T> String.fromJson(): T = gson.fromJson(this,
        makeType<T>()
)

fun Context.getWindowWidth(): Int {
    val display = (this as Activity).windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)

    return size.x
}