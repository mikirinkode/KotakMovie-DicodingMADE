package com.mikirinkode.kotakfilm.data

import androidx.lifecycle.LiveData
import com.mikirinkode.kotakfilm.data.model.MovieEntity
import com.mikirinkode.kotakfilm.data.model.TvShowEntity
import com.mikirinkode.kotakfilm.vo.Resource

interface MovieDataSource {

    fun getPopularMoviesList(sort: String): LiveData<Resource<List<MovieEntity>>>

    fun getMovieDetail(movieId: Int): LiveData<Resource<MovieEntity>>

    fun getFavoriteMovies(): LiveData<List<MovieEntity>>

    fun setFavoriteMovie(movie: MovieEntity, state: Boolean)


    fun getPopularTvShowsList(sort: String): LiveData<Resource<List<TvShowEntity>>>

    fun getTvShowDetail(tvShowId: Int): LiveData<Resource<TvShowEntity>>

    fun getFavoriteTvShows(): LiveData<List<TvShowEntity>>

    fun setFavoriteTvShow(tvShow: TvShowEntity, state: Boolean)
}