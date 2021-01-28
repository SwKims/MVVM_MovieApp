package com.ksw.movielist.ui.popularMovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ksw.movielist.R
import com.ksw.movielist.data.api.TheMovieDBClient
import com.ksw.movielist.data.api.TheMovieDBInterface
import com.ksw.movielist.data.repository.NetworkState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    lateinit var movieRepository: MoviePageList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService : TheMovieDBInterface = TheMovieDBClient.getClient()

        movieRepository = MoviePageList(apiService)

        viewModel = getViewModel()

        val movieAdapter = MoviePagedListAdapter(this)

        val gridLayoutManager = GridLayoutManager(this, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.MOVIE_VIEW_TYPE) return  1
                else return 3
            }
        }

        rv_movieList.layoutManager = gridLayoutManager
        rv_movieList.setHasFixedSize(true)
        rv_movieList.adapter = movieAdapter

        viewModel.moviePageList.observe(this, Observer {
            movieAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            progressBar_popular.visibility = if (viewModel.listEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            tv_error_popular.visibility = if (viewModel.listEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })

    }

    private fun getViewModel(): MainViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(movieRepository) as T
            }
        })[MainViewModel::class.java]
    }



}