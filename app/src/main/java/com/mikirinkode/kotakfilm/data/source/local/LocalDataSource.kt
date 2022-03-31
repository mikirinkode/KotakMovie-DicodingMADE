package com.mikirinkode.kotakfilm.data.source.local

import androidx.lifecycle.LiveData
import com.mikirinkode.kotakfilm.data.model.MovieEntity
import com.mikirinkode.kotakfilm.data.model.TvShowEntity
import com.mikirinkode.kotakfilm.utils.SortUtils
import com.mikirinkode.kotakfilm.utils.SortUtils.MOVIE_TABLE
import com.mikirinkode.kotakfilm.utils.SortUtils.TV_TABLE
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val mMovieDao: MovieDao) {

    companion object{
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieDao)
    }

    fun getMovieList(sort: String): LiveData<List<MovieEntity>> = mMovieDao.getPopularMovies(
        SortUtils.getSortedQuery(sort, MOVIE_TABLE))
    fun getUpcomingMovies(): LiveData<List<MovieEntity>> = mMovieDao.getUpcomingMovies()

    fun getMovieDetail(movieId: Int): LiveData<MovieEntity> = mMovieDao.getMovieDetail(movieId)

    fun getFavoriteMovieList(): LiveData<List<MovieEntity>> = mMovieDao.getFavoriteMovies()

    fun insertMovieList(movies: List<MovieEntity>) = mMovieDao.insertMovieList(movies)

    fun updateMovieData(movie: MovieEntity) = mMovieDao.updateMovie(movie)

    fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        mMovieDao.updateMovie(movie)
    }



    fun getTvShowList(sort: String): LiveData<List<TvShowEntity>> = mMovieDao.getPopularTvShows(
        SortUtils.getSortedQuery(sort, TV_TABLE))

    fun getTvShowDetail(tvShowId: Int): LiveData<TvShowEntity> = mMovieDao.getTvShowDetail(tvShowId)

    fun getAiringTodayTvShows(): LiveData<List<TvShowEntity>> = mMovieDao.getAiringTodayTvShows()

    fun getFavoriteTvShowList(): LiveData<List<TvShowEntity>> = mMovieDao.getFavoriteTvShows()

    fun insertTvShowList(tvShows: List<TvShowEntity>) = mMovieDao.insertTvShowList(tvShows)

    fun updateTvShowData(tvShow: TvShowEntity) = mMovieDao.updateTvShow(tvShow)

    fun setFavoriteTvShow(tvShow: TvShowEntity, newState: Boolean) {
        tvShow.isFavorite = newState
        mMovieDao.updateTvShow(tvShow)
    }
}

