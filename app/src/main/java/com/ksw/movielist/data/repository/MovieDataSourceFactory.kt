package com.ksw.movielist.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.ksw.movielist.data.api.TheMovieDBInterface
import com.ksw.movielist.data.value.Movie
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by KSW on 2021-01-28
 */

class MovieDataSourceFactory(
    private val apiService: TheMovieDBInterface,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService, compositeDisposable)

        moviesLiveDataSource.postValue(movieDataSource)

        return movieDataSource
    }


}