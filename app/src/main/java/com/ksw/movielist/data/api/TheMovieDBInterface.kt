package com.ksw.movielist.data.api

import com.ksw.movielist.data.value.MovieDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by KSW on 2021-01-27
 */

/**
https://api.themoviedb.org/3/movie/popular?api_key=4defd070082d28cda9bb902140bcf8d0&language=ko-KR
https://api.themoviedb.org/3/movie/299534?api_key=4defd070082d28cda9bb902140bcf8d0&language=ko-KR
 */

interface TheMovieDBInterface {

    @GET("movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") id: Int): Single<MovieDetails>


}