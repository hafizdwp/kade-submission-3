package me.hafizdwp.kade_submission_3.mvvm.search

import me.hafizdwp.kade_submission_3.R
import me.hafizdwp.kade_submission_3.base.BaseRecyclerAdapter
import me.hafizdwp.kade_submission_3.data.source.remote.model.MatchResponse
import me.hafizdwp.kade_submission_3.databinding.SearchItemBinding

/**
 * @author hafizdwp
 * 07/11/2019
 **/
class SearchAdapter(val items: List<MatchResponse>,
                    val listener: SearchActionListener) : BaseRecyclerAdapter<MatchResponse, SearchItemBinding>() {

    override val bindItemLayoutRes: Int?
        get() = R.layout.search_item
    override val mListItem: List<MatchResponse>
        get() = items

    override fun onBind(itemBinding: SearchItemBinding, model: MatchResponse) {
        itemBinding.apply {
            data = model
            actionListener = listener
        }
    }
}