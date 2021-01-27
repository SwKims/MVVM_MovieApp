package com.ksw.movielist.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ksw.movielist.data.api.TheMovieDBInterface
import com.ksw.movielist.data.value.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by KSW on 2021-01-27
 */

// network
class MovieDetailsNetworkDataSource(
    private val apiService: TheMovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadMovieDetail = MutableLiveData<MovieDetails>()
    val downloadMovieDetail: LiveData<MovieDetails>
        get() = _downloadMovieDetail

    fun fetchMovieDetail(movieId : Int) {

        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getMovieDetail(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadMovieDetail.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("fetchMovieDetail: ", it.message.toString())
                        }
                    )
            )
        }

        catch (e : Exception) {
            Log.e("fetchMovieDetail: ", e.message.toString() )
        }

    }

}