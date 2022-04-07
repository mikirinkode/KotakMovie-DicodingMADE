package com.mikirinkode.kotakfilm.core.data.source.local

import androidx.lifecycle.LiveData
import com.mikirinkode.kotakfilm.core.data.entity.CatalogueEntity
import com.mikirinkode.kotakfilm.core.data.entity.TrailerVideoEntity
import com.mikirinkode.kotakfilm.core.utils.SortUtils
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val mMovieDao: MovieDao) {

    companion object{
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieDao)
    }

    fun getMovieList(sort: String): LiveData<List<CatalogueEntity>> = mMovieDao.getPopularMovies(
        SortUtils.getSortedQuery(sort, 0))

    fun getMovieDetail(movieId: Int): LiveData<CatalogueEntity> = mMovieDao.getMovieDetail(movieId)

    fun getMoviePlaylist(): LiveData<List<CatalogueEntity>> = mMovieDao.getMoviePlaylist()

    fun insertMovieList(movies: List<CatalogueEntity>) = mMovieDao.insertMovieList(movies)

    fun updateMovieData(movie: CatalogueEntity) = mMovieDao.updateMovie(movie)

    fun setMoviePlaylist(movie: CatalogueEntity, newState: Boolean) {
        movie.isOnPlaylist = newState
        mMovieDao.updateMovie(movie)
    }


    fun insertVideoTrailer(trailer: TrailerVideoEntity) = mMovieDao.insertVideoTrailer(trailer)

    fun getVideoTrailer(catalogueId: Int): LiveData<List<TrailerVideoEntity>> = mMovieDao.getVideoTrailer(catalogueId)



    fun getTvShowList(sort: String): LiveData<List<CatalogueEntity>> = mMovieDao.getPopularTvShows(
        SortUtils.getSortedQuery(sort, 1))

    fun getTvShowDetail(tvShowId: Int): LiveData<CatalogueEntity> = mMovieDao.getTvShowDetail(tvShowId)

    fun getTvShowPlaylist(): LiveData<List<CatalogueEntity>> = mMovieDao.getTvShowPlaylist()

    fun insertTvShowList(tvShows: List<CatalogueEntity>) = mMovieDao.insertTvShowList(tvShows)

    fun updateTvShowData(tvShow: CatalogueEntity) = mMovieDao.updateTvShow(tvShow)

    fun setTvShowPlaylist(tvShow: CatalogueEntity, newState: Boolean) {
        tvShow.isOnPlaylist = newState
        mMovieDao.updateTvShow(tvShow)
    }
}

