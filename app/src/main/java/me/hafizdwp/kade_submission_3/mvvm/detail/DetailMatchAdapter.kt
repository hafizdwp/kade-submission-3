package me.hafizdwp.kade_submission_3.mvvm.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import me.hafizdwp.kade_submission_3.R
import me.hafizdwp.kade_submission_3.base.BaseRecyclerAdapter
import me.hafizdwp.kade_submission_3.databinding.DetailMatchAwayItemBinding
import me.hafizdwp.kade_submission_3.databinding.DetailMatchHomeItemBinding

/**
 * @author hafizdwp
 * 05/11/2019
 **/
class DetailMatchAdapter(val items: List<DetailMatchGoals>) : BaseRecyclerAdapter<DetailMatchGoals, ViewDataBinding>() {

    override val bindItemLayoutRes: Int?
        get() = R.layout.detail_match_home_item
    override val mListItem: List<DetailMatchGoals>
        get() = items
    override val useOnBindCustom: Boolean
        get() = true

    object ViewType {
        const val HOME = 1
        const val AWAY = 2
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType == ViewType.HOME)
            BaseViewHolder(
                    DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.detail_match_home_item, parent, false))
        else
            BaseViewHolder(
                    DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.detail_match_away_item, parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position].type) {
            DetailMatchGoals.Type.HOME -> ViewType.HOME
            DetailMatchGoals.Type.AWAY -> ViewType.AWAY
        }
    }

    override fun onBind(itemBinding: ViewDataBinding, model: DetailMatchGoals) {}
    override fun onBindCustom(itemBinding: ViewDataBinding, model: DetailMatchGoals, layoutPosition: Int) {
        when (getItemViewType(layoutPosition)) {
            ViewType.HOME ->
                bindHome(itemBinding, items[layoutPosition])
            ViewType.AWAY ->
                bindAway(itemBinding, items[layoutPosition])
        }
    }

    private fun bindHome(itemBinding: ViewDataBinding, detailMatchGoals: DetailMatchGoals) {
        (itemBinding as DetailMatchHomeItemBinding).apply {
            data = detailMatchGoals
            executePendingBindings()
        }
    }

    private fun bindAway(itemBinding: ViewDataBinding, detailMatchGoals: DetailMatchGoals) {
        (itemBinding as DetailMatchAwayItemBinding).apply {
            data = detailMatchGoals
            executePendingBindings()
        }
    }
}