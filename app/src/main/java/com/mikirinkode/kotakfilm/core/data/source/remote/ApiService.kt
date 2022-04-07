package com.mikirinkode.kotakfilm.core.data.source.remote

import com.mikirinkode.kotakfilm.core.data.source.remote.response.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/movie")
    fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ):Call<MovieListResponse>

    @GET("movie/popular")
    fun getPopularMovieList(
        @Query("api_key") apiKey: String
    ): Call<MovieListResponse>

    @GET("movie/upcoming")
    fun getUpcomingMovieList(
        @Query("api_key") apiKey: String
    ): Call<MovieListResponse>

    @GET("trending/movie/week")
    fun getTrendingMovieList(
        @Query("api_key") apiKey: String
    ): Call<MovieListResponse>

    @GET("movie/{movie_id}")
    fun getDetailMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<MovieDetailResponse>

    @GET("movie/{movie_id}/videos")
    fun getMovieTrailer(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Call<TrailerVideoResponse>


    @GET("tv/{tv_id}/videos")
    fun getTvShowTrailer(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String
    ): Call<TrailerVideoResponse>

    @GET("tv/popular")
    fun getPopularTvShowList(
        @Query("api_key") apiKey: String
    ): Call<TvShowListResponse>

    @GET("tv/top_rated")
    fun getTopTvShowList(
        @Query("api_key") apiKey: String
    ): Call<TvShowListResponse>

    @GET("tv/{tv_id}")
    fun getDetailTvShow(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String
    ): Call<TvShowDetailResponse>

}
