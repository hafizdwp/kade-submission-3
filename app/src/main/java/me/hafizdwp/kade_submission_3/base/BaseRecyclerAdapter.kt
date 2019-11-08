package me.hafizdwp.kade_submission_3.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * @author hafizdwp
 * 29/10/2019
 **/
abstract class BaseRecyclerAdapter<MODEL, BINDING : ViewDataBinding>
    : RecyclerView.Adapter<BaseRecyclerAdapter<MODEL, BINDING>.BaseViewHolder>() {

    @get:LayoutRes
    abstract val bindItemLayoutRes: Int?
    abstract val mListItem: List<MODEL>

    /**
     * Default binding layout function
     * */
    abstract fun onBind(itemBinding: BINDING, model: MODEL)

    /**
     * Custom binding layout (currrently with extra param layoutPosition)
     *
     * Override this useOnBindCustom and set it to true
     * Override onBindCustom after it
     * */
    open val useOnBindCustom: Boolean = false

    open fun onBindCustom(itemBinding: BINDING, model: MODEL, layoutPosition: Int) {}

    /**
     * Override this if you want to return item count
     * from different way
     * */
    open fun onGetItemCount(): Int {
        return mListItem.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = DataBindingUtil.inflate<BINDING>(
            LayoutInflater.from(parent.context),
            bindItemLayoutRes ?: 0,
            parent,
            false
        )

        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(mListItem[position])
    }

    override fun getItemCount(): Int {
        return onGetItemCount()
    }

    inner class BaseViewHolder(val itemBinding: BINDING) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(model: MODEL) {

            if (!useOnBindCustom)
                onBind(itemBinding, model)
            else
                onBindCustom(itemBinding, model, layoutPosition)
        }
    }
}