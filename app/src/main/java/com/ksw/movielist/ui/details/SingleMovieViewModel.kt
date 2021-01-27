package com.ksw.movielist.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ksw.movielist.data.repository.NetworkState
import com.ksw.movielist.data.value.MovieDetails
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by KSW on 2021-01-27
 */

class SingleMovieViewModel
    (private val movieDetailsRepository: MovieDetailsRepository, movieId: Int) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieDetails: LiveData<MovieDetails> by lazy {
        movieDetailsRepository.fetchSingleMovieDetails(compositeDisposable, movieId)
    }
    val networkState: LiveData<NetworkState> by lazy {
        movieDetailsRepository.getMovieDetailNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}