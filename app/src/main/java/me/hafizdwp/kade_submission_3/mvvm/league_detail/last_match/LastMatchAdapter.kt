package me.hafizdwp.kade_submission_3.mvvm.league_detail.last_match

import com.bumptech.glide.Glide
import me.hafizdwp.kade_submission_3.R
import me.hafizdwp.kade_submission_3.base.BaseRecyclerAdapter
import me.hafizdwp.kade_submission_3.data.source.remote.model.MatchResponse
import me.hafizdwp.kade_submission_3.databinding.LastMatchItemBinding
import me.hafizdwp.kade_submission_3.mvvm.league_detail.LeagueDetailActionListener
import me.hafizdwp.kade_submission_3.util.ext.withLoadingPlaceholder

/**
 * @author hafizdwp
 * 03/11/2019
 **/
class LastMatchAdapter(val items: List<MatchResponse>,
                       val listener: LeagueDetailActionListener) : BaseRecyclerAdapter<MatchResponse, LastMatchItemBinding>() {

    override val bindItemLayoutRes: Int?
        get() = R.layout.last_match_item
    override val mListItem: List<MatchResponse>
        get() = items

    override fun onBind(itemBinding: LastMatchItemBinding, model: MatchResponse) {
        itemBinding.apply {
            data = model
            actionListener = listener

            Glide.with(root.context)
                    .load(model.strThumb)
                    .requestOptions {
                        withLoadingPlaceholder(root.context)
                        error(R.drawable.no_image_found)
                    }
                    .into(imgCover)

            textHomeHero.text = model.strHomeGoalDetails?.replace(";", "\n")
            textAwayHero.text = model.strAwayGoalDetails?.replace(";", "\n")
        }
    }
}