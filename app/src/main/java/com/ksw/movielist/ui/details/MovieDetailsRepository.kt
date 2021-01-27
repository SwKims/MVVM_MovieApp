package com.ksw.movielist.ui.details

import androidx.lifecycle.LiveData
import com.ksw.movielist.data.api.TheMovieDBInterface
import com.ksw.movielist.data.repository.MovieDetailsNetworkDataSource
import com.ksw.movielist.data.repository.NetworkState
import com.ksw.movielist.data.value.MovieDetails
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by KSW on 2021-01-27
 */

class MovieDetailsRepository(private val apiService: TheMovieDBInterface) {

    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails(compositeDisposable: CompositeDisposable, movieId: Int): LiveData<MovieDetails> {
        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService, compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetail(movieId)

        return movieDetailsNetworkDataSource.downloadMovieDetail
    }

    fun getMovieDetailNetworkState() : LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }

}