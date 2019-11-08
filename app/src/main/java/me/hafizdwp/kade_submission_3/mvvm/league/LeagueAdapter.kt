package me.hafizdwp.kade_submission_3.mvvm.league

import me.hafizdwp.kade_submission_3.R
import me.hafizdwp.kade_submission_3.base.BaseRecyclerAdapter
import me.hafizdwp.kade_submission_3.databinding.LeagueItemBinding

/**
 * @author hafizdwp
 * 30/10/2019
 **/
class LeagueAdapter(val listener: LeagueActionListener) : BaseRecyclerAdapter<LeagueData, LeagueItemBinding>() {

    override val bindItemLayoutRes: Int?
        get() = R.layout.league_item
    override val mListItem: List<LeagueData>
        get() = LeagueData.getAll()

    override fun onBind(itemBinding: LeagueItemBinding, model: LeagueData) {
        itemBinding.apply {
            imgIcon.setImageResource(model.imgRes)
            textName.text = model.name
            root.setOnClickListener {
                listener.onLeagueClick(model)
            }
        }
    }
}