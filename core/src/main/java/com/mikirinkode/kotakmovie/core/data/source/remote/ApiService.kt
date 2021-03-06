package com.mikirinkode.kotakmovie.core.data.source.remote

import com.mikirinkode.kotakmovie.core.data.source.remote.response.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/multi")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean
    ): MultiResponse

    @GET("movie/popular")
    suspend fun getPopularMovieList(
        @Query("api_key") apiKey: String
    ): MovieListResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovieList(
        @Query("api_key") apiKey: String
    ): MovieListResponse

    @GET("trending/all/week")
    suspend fun getTrendingThisWeekList(
        @Query("api_key") apiKey: String
    ): MultiResponse

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): MovieDetailResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieTrailer(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): TrailerVideoResponse


    @GET("tv/{tv_id}/videos")
    suspend fun getTvShowTrailer(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String
    ): TrailerVideoResponse

    @GET("tv/popular")
    suspend fun getPopularTvShowList(
        @Query("api_key") apiKey: String
    ): TvShowListResponse

    @GET("tv/top_rated")
    suspend fun getTopTvShowList(
        @Query("api_key") apiKey: String
    ): TvShowListResponse

    @GET("tv/{tv_id}")
    suspend fun getDetailTvShow(
        @Path("tv_id") tvShowId: Int,
        @Query("api_key") apiKey: String
    ): TvShowDetailResponse

}
