package com.ksw.movielist.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ksw.movielist.R
import com.ksw.movielist.data.api.POSTER_BASE_URL
import com.ksw.movielist.data.api.TheMovieDBInterface
import com.ksw.movielist.data.api.TheMovieDBClient
import com.ksw.movielist.data.repository.NetworkState
import com.ksw.movielist.data.value.MovieDetails
import kotlinx.android.synthetic.main.activity_single_movie.*
import java.text.NumberFormat
import java.util.*

class SingleMovie : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        val movieId: Int = intent.getIntExtra("id" , 1)

        val apiService: TheMovieDBInterface = TheMovieDBClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)

        viewModel = getViewModel(movieId)
        viewModel.movieDetails.observe(this, Observer {
            bind(it)
        })

        viewModel.networkState.observe(this, {
            progressBar.visibility = if (it == NetworkState.LOADING) {
                View.VISIBLE
            } else {
                View.GONE
            }
            tv_error.visibility = if (it == NetworkState.ERROR) {
                View.VISIBLE
            } else {
                View.GONE
            }
        })
    }

    fun bind(it: MovieDetails) {
        tv_movieTitle.text = it.title
        tv_movieTag.text = it.tagline
        tv_movieReleaseDate.text = it.releaseDate
        tv_movieRating.text = it.rating.toString()
        tv_movieRuntime.text = it.runtime.toString() + " 분"
        tv_movieOverview.text = it.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.KOREAN)
/*        tv_movieBudget.text = formatCurrency.format(it.budget)
        tv_movieRevenue.text = formatCurrency.format(it.revenue)*/

        val moviePosterUrl = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterUrl)
            .into(iv_moviePoster)
    }

    private fun getViewModel(movieId: Int): SingleMovieViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(movieRepository, movieId) as T
            }
        })[SingleMovieViewModel::class.java]
    }
}