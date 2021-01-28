package com.ksw.movielist.ui.popularMovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ksw.movielist.data.api.PAGE_END
import com.ksw.movielist.data.api.TheMovieDBInterface
import com.ksw.movielist.data.repository.MovieDataSource
import com.ksw.movielist.data.repository.MovieDataSourceFactory
import com.ksw.movielist.data.repository.NetworkState
import com.ksw.movielist.data.value.Movie
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by KSW on 2021-01-28
 */

class MoviePageList(private val apiService : TheMovieDBInterface) {

    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>> {
        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_END)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList

    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState)
    }

}