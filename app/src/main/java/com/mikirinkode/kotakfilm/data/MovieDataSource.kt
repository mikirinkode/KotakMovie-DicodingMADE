package com.mikirinkode.kotakfilm.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.mikirinkode.kotakfilm.data.model.MovieEntity
import com.mikirinkode.kotakfilm.data.model.TvShowEntity
import com.mikirinkode.kotakfilm.vo.Resource

interface MovieDataSource {

    fun getPopularMoviesList(sort: String): LiveData<Resource<PagedList<MovieEntity>>>

    fun getMovieDetail(movieId: Int): LiveData<Resource<MovieEntity>>

    fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>>

    fun setFavoriteMovie(movie: MovieEntity, state: Boolean)


    fun getPopularTvShowsList(sort: String): LiveData<Resource<PagedList<TvShowEntity>>>

    fun getTvShowDetail(tvShowId: Int): LiveData<Resource<TvShowEntity>>

    fun getFavoriteTvShows(): LiveData<PagedList<TvShowEntity>>

    fun setFavoriteTvShow(tvShow: TvShowEntity, state: Boolean)
}