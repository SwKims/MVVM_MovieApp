package com.ksw.movielist.ui.popularMovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ksw.movielist.data.repository.NetworkState
import com.ksw.movielist.data.value.Movie
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by KSW on 2021-01-28
 */

class MainViewModel(private val movieRepository : MoviePageList) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val moviePageList : LiveData<PagedList<Movie>> by lazy {
        movieRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val networkState : LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }

    fun listEmpty() : Boolean {
        return moviePageList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


}