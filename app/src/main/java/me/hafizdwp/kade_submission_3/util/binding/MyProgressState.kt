package me.hafizdwp.kade_submission_3.util.binding

/**
 * @author hafizdwp
 * 27/09/2019
 **/
sealed class MyProgressState {
    object Loading : MyProgressState()
    object Success : MyProgressState()
    class Empty(val emptyMsg: String?) : MyProgressState()
    class Failed(val errorMsg: String?) : MyProgressState()
}

