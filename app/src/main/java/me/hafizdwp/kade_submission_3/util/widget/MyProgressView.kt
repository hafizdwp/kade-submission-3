package me.hafizdwp.kade_submission_3.util.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import me.hafizdwp.kade_submission_3.R
import me.hafizdwp.kade_submission_3.util.ext.gone
import me.hafizdwp.kade_submission_3.util.ext.visible

/**
 * Created by teddy on 10/23/15.
 */
class MyProgressView(internal var ctx: Context, attrs: AttributeSet) : RelativeLayout(ctx, attrs) {

    private var ivProgress: ViewGroup
    private var ivProgressAnim: ImageView
    private var tvMessage: TextView
    private var tvMessageDesc: TextView
    private var btnRetry: Button
    private var ivMessage: ImageView

    private var anim: Animation

    init {
        val v = LayoutInflater.from(ctx).inflate(R.layout.view_progress, null)

        // progress
        ivProgress = v.findViewById(R.id.progress)
        ivProgressAnim = v.findViewById(R.id.progress_anim)
        btnRetry = v.findViewById(R.id.btn_retry)
        tvMessage = v.findViewById(R.id.tv_message)
        tvMessageDesc = v.findViewById(R.id.tv_message_desc)
        ivMessage = v.findViewById(R.id.iv_message)

        anim = AnimationUtils.loadAnimation(ctx, R.anim.rotate_anim)
        ivProgressAnim.startAnimation(anim)

        addView(v)
    }

    private enum class ErrorType {
        NO_INTERNET, NO_CONTENT, ELSE
    }

    fun startProgress() {
        this.visible()
        ivProgress.visible()
        btnRetry.gone()
        tvMessage.gone()
        ivMessage.gone()
        tvMessageDesc.gone()

        ivProgressAnim.startAnimation(anim)
    }

    fun startProgress(msg: String) {
        this.visible()
        ivProgress.visible()
        btnRetry.gone()
        ivMessage.gone()
        tvMessage.text = msg

        ivProgressAnim.startAnimation(anim)
    }

    fun stopAndGone() {
        this.gone()
    }

    fun stopAndError(errorMessage: String?,
                     isRetry: Boolean,
                     withImage: Boolean = false) {
        ivProgress.clearAnimation()
        ivProgress.gone()
        if (isRetry)
            btnRetry.visible()
        else
            btnRetry.gone()

        tvMessage.visible()
        tvMessageDesc.visible()

        if (withImage)
            ivMessage.visible()
        else
            ivMessage.gone()

        // Decide message, message-desc, and error-image
        // by error message
        when (getErrorTypeByMessage(errorMessage)) {
            ErrorType.NO_INTERNET -> {
                tvMessage.text = resources.getString(R.string.error_message_no_internet_connection)
                tvMessageDesc.text = resources.getString(R.string.error_desc_no_internet_connection)
                //ivMessage.setImageResource(R.drawable._img_no_internet)
            }

            ErrorType.NO_CONTENT -> {
                tvMessage.text = resources.getString(R.string.error_message_no_content)
                tvMessageDesc.text = resources.getString(R.string.error_desc_no_content)
                //ivMessage.setImageResource(R.drawable._img_no_content)
            }

            ErrorType.ELSE -> {
                tvMessage.text = resources.getString(R.string.error_message_oops)
                tvMessageDesc.text = errorMessage
                //ivMessage.setImageResource(R.drawable._img_error)
            }
        }
    }

    fun setRetryClickListener(todo: (view: View?) -> Unit) {
        btnRetry.setOnClickListener(null)
        btnRetry.setOnClickListener { view ->
            todo(view)
        }
    }

    private fun getErrorTypeByMessage(errorMessage: String?): ErrorType {
        return when (errorMessage) {
            resources.getString(R.string.error_message_no_internet_connection) -> ErrorType.NO_INTERNET
            resources.getString(R.string.error_message_no_content) -> ErrorType.NO_CONTENT
            resources.getString(R.string.error_desc_no_data) -> ErrorType.NO_CONTENT
            else -> ErrorType.ELSE
        }
    }
}


