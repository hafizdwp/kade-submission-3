package me.hafizdwp.kade_submission_3.util.binding

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import me.hafizdwp.kade_submission_3.R
import me.hafizdwp.kade_submission_3.base.BaseProgressListener
import me.hafizdwp.kade_submission_3.util.ext.getWindowWidth
import me.hafizdwp.kade_submission_3.util.ext.log
import me.hafizdwp.kade_submission_3.util.ext.requestOptions
import me.hafizdwp.kade_submission_3.util.ext.withLoadingPlaceholder
import me.hafizdwp.kade_submission_3.util.widget.MyProgressView

/**
 * @author hafizdwp
 * 29/10/2019
 **/
object MyBindings {

    @SuppressLint("CheckResult")
    @BindingAdapter("bind:image")
    @JvmStatic
    fun ImageView.setLoadImage(imageUrl: String?) {
        Glide.with(this.context)
                .load(imageUrl)
                .withLoadingPlaceholder(this.context)
                .into(this)
    }

    @BindingAdapter("bind:progressState", "bind:progressListener", "bind:isSwipe", "bind:withImage", requireAll = false)
    @JvmStatic
    fun MyProgressView.setProgressState(myProgressState: MyProgressState?,
                                        listener: BaseProgressListener?,
                                        isSwipe: Boolean? = false,
                                        withImage: Boolean? = true) {

        if (isSwipe == null || isSwipe == false) {
            when (myProgressState) {
                null -> {
                }
                is MyProgressState.Loading ->
                    startProgress()
                is MyProgressState.Success ->
                    stopAndGone()
                is MyProgressState.Empty ->
                    stopAndError(myProgressState.emptyMsg, false, withImage ?: false)
                is MyProgressState.Failed -> {
                    stopAndError(myProgressState.errorMsg, true, withImage ?: false)
                    this.setRetryClickListener {
                        listener?.onFailedRetryClick()
                    }
                }
            }
        }
    }


    @SuppressLint("CheckResult")
    @BindingAdapter(
            "bind:loadImage",
            "bind:isOriginalImageSize",
            "bind:fitCenterCrop", requireAll = false)
    @JvmStatic
    fun ImageView.setLoadImage(imageUrl: String?, isOriginalImageSize: Boolean? = false, fitCenterCrop: Boolean?) {
        Glide.with(this.context)
                .load(imageUrl)
                .requestOptions {
                    if (fitCenterCrop == null || fitCenterCrop == true) {
                        this.fitCenter()
                        this.centerCrop()
                    }

                    if (isOriginalImageSize == true)
                        override(context.getWindowWidth(), Target.SIZE_ORIGINAL)
                    withLoadingPlaceholder(this@setLoadImage.context)
                    error(R.drawable.no_image_found)
                }
                .into(this)
    }

    @BindingAdapter("bind:textLineups")
    @JvmStatic
    fun TextView.setTextLineups(string: String?) {
        string?.let {
            val stringAppend = StringBuilder()
            val nameInArray = string.split(";").toMutableList()
            nameInArray.removeAt(nameInArray.lastIndex)
            nameInArray.forEachIndexed { index, s ->
                if (nameInArray.size == 1)
                    stringAppend.append(s.trim())
                else {
                    if (index == nameInArray.lastIndex)
                        stringAppend.append(s.trim())
                    else
                        stringAppend.append(s.trim() + "\n")
                }
            }

            log(stringAppend.toString())
            text = stringAppend.toString()
        }
    }
}