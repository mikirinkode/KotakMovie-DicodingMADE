package com.mikirinkode.kotakfilm.data

import androidx.lifecycle.LiveData
import com.mikirinkode.kotakfilm.data.model.MovieEntity
import com.mikirinkode.kotakfilm.data.model.TvShowEntity
import com.mikirinkode.kotakfilm.vo.Resource

interface MovieDataSource {

    fun getPopularMovies(sort: String): LiveData<Resource<List<MovieEntity>>>

    fun getUpcomingMovies(): LiveData<Resource<List<MovieEntity>>>

    fun getMovieDetail(movie: MovieEntity): LiveData<Resource<MovieEntity>>

    fun getFavoriteMovies(): LiveData<List<MovieEntity>>

    fun setFavoriteMovie(movie: MovieEntity, state: Boolean)


    fun getPopularTvShows(sort: String): LiveData<Resource<List<TvShowEntity>>>

    fun getAiringTodayTvShows(): LiveData<Resource<List<TvShowEntity>>>

    fun getTvShowDetail(tvShow: TvShowEntity): LiveData<Resource<TvShowEntity>>

    fun getFavoriteTvShows(): LiveData<List<TvShowEntity>>

    fun setFavoriteTvShow(tvShow: TvShowEntity, state: Boolean)
}