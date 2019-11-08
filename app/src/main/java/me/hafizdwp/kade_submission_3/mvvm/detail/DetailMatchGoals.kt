package me.hafizdwp.kade_submission_3.mvvm.detail

/**
 * @author hafizdwp
 * 05/11/2019
 **/
data class DetailMatchGoals(
        val minute: String,
        val goalScorer: String,
        val type: Type
) {
    enum class Type {
        HOME, AWAY
    }
}