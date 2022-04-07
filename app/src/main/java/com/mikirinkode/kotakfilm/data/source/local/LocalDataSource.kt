package com.mikirinkode.kotakfilm.data.source.local

import androidx.lifecycle.LiveData
import com.mikirinkode.kotakfilm.data.model.CatalogueEntity
import com.mikirinkode.kotakfilm.data.model.TrailerVideoEntity
import com.mikirinkode.kotakfilm.utils.SortUtils
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val mMovieDao: MovieDao) {

    companion object{
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieDao)
    }

    fun getMovieList(sort: String): LiveData<List<CatalogueEntity>> = mMovieDao.getPopularMovies(
        SortUtils.getSortedQuery(sort, false))

    fun searchMovies(query: String): LiveData<List<CatalogueEntity>> = mMovieDao.searchMovies(query)

    fun getUpcomingMovies(): LiveData<List<CatalogueEntity>> = mMovieDao.getUpcomingMovies()

    fun getTrendingMovies(): LiveData<List<CatalogueEntity>> = mMovieDao.getTrendingMovies()

    fun getMovieDetail(movieId: Int): LiveData<CatalogueEntity> = mMovieDao.getMovieDetail(movieId)

    fun getMoviePlaylist(): LiveData<List<CatalogueEntity>> = mMovieDao.getMoviePlaylist()

    fun insertMovieList(movies: List<CatalogueEntity>) = mMovieDao.insertMovieList(movies)

    fun insertSearchResult(movie: CatalogueEntity) = mMovieDao.insertSearchResult(movie)

    fun updateMovieData(movie: CatalogueEntity) = mMovieDao.updateMovie(movie)

    fun setMoviePlaylist(movie: CatalogueEntity, newState: Boolean) {
        movie.isOnPlaylist = newState
        mMovieDao.updateMovie(movie)
    }


    fun insertVideoTrailer(trailer: TrailerVideoEntity) = mMovieDao.insertVideoTrailer(trailer)

    fun getVideoTrailer(catalogueId: Int): LiveData<List<TrailerVideoEntity>> = mMovieDao.getVideoTrailer(catalogueId)



    fun getTvShowList(sort: String): LiveData<List<CatalogueEntity>> = mMovieDao.getPopularTvShows(
        SortUtils.getSortedQuery(sort, true))

    fun getTvShowDetail(tvShowId: Int): LiveData<CatalogueEntity> = mMovieDao.getTvShowDetail(tvShowId)

    fun getTopTvShowList(): LiveData<List<CatalogueEntity>> = mMovieDao.getTopTvShowList()

    fun getTvShowPlaylist(): LiveData<List<CatalogueEntity>> = mMovieDao.getTvShowPlaylist()

    fun insertTvShowList(tvShows: List<CatalogueEntity>) = mMovieDao.insertTvShowList(tvShows)

    fun updateTvShowData(tvShow: CatalogueEntity) = mMovieDao.updateTvShow(tvShow)

    fun setTvShowPlaylist(tvShow: CatalogueEntity, newState: Boolean) {
        tvShow.isOnPlaylist = newState
        mMovieDao.updateTvShow(tvShow)
    }
}

