package me.hafizdwp.kade_submission_3.base

/**
 * @author hafizdwp
 * 29/10/2019
 **/
interface BaseRecyclerSwipeLoadMoreListener {

    fun onSwipe()

    // Override this if your view need onLoadMore
    fun onLoadMore(page: Int) {
    }
}