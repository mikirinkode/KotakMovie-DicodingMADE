package com.mikirinkode.kotakfilm.data.source.remote

import com.mikirinkode.kotakfilm.data.source.remote.response.MovieDetailResponse
import com.mikirinkode.kotakfilm.data.source.remote.response.MovieListResponse
import com.mikirinkode.kotakfilm.data.source.remote.response.TvShowDetailResponse
import com.mikirinkode.kotakfilm.data.source.remote.response.TvShowListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getPopularMoviesList(
        @Query("api_key") apiKey: String
    ): Call<MovieListResponse>


    @GET("movie/{movieId}")
    fun getDetailMovie(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<MovieDetailResponse>


    @GET("tv/popular")
    fun getPopularTvShowsList(
        @Query("api_key") apiKey: String
    ): Call<TvShowListResponse>

    @GET("tv/{tvShowId}")
    fun getDetailTvShow(
        @Path("tvShowId") tvShowId: Int,
        @Query("api_key") apiKey: String
    ): Call<TvShowDetailResponse>

}
