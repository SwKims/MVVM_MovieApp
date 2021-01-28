package com.ksw.movielist.data.repository

/**
 * Created by KSW on 2021-01-27
 */

enum class Status {
    RUNNING, SUCCESS, FAILED
}

class NetworkState(val status : Status, val msg : String) {

    companion object {
        val LOADED : NetworkState = NetworkState(Status.SUCCESS, "성공")
        val LOADING : NetworkState = NetworkState(Status.RUNNING, "로딩중")
        val ERROR : NetworkState = NetworkState(Status.FAILED, "실패")
        val END : NetworkState = NetworkState(Status.FAILED, "데이터의 끝에 도달했습니다.")
    }

}