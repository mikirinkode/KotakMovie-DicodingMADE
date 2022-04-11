package com.mikirinkode.kotakfilm.core.data.source.local

import androidx.lifecycle.LiveData
import com.mikirinkode.kotakfilm.core.data.entity.CatalogueEntity
import com.mikirinkode.kotakfilm.core.data.entity.TrailerVideoEntity
import com.mikirinkode.kotakfilm.core.utils.SortUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val mMovieDao: MovieDao) {

    companion object{
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieDao)
    }

    fun getMovieList(sort: String): Flow<List<CatalogueEntity>> = mMovieDao.getPopularMovies(
        SortUtils.getSortedQuery(sort, 0))

    fun getMoviePlaylist(): Flow<List<CatalogueEntity>> = mMovieDao.getMoviePlaylist()

    suspend fun insertMovieList(movies: List<CatalogueEntity>) = mMovieDao.insertCatalogueList(movies)

//    suspend fun setMoviePlaylist(movie: CatalogueEntity, newState: Boolean) {
//        movie.isOnPlaylist = newState
//        mMovieDao.insertNewPlaylistItem(movie)
//    }

    suspend fun insertPlaylistItem(item: CatalogueEntity, newState: Boolean) {
        item.isOnPlaylist = newState
        mMovieDao.insertNewPlaylistItem(item)
    }

    suspend fun removeItemFromPlaylist(item: CatalogueEntity){
        mMovieDao.removePlaylistItem(item)
    }


    fun getTvShowList(sort: String): Flow<List<CatalogueEntity>> = mMovieDao.getPopularTvShows(
        SortUtils.getSortedQuery(sort, 1))

    fun getTvShowPlaylist(): Flow<List<CatalogueEntity>> = mMovieDao.getTvShowPlaylist()

    suspend fun insertTvShowList(tvShows: List<CatalogueEntity>) = mMovieDao.insertCatalogueList(tvShows)

//    suspend fun setTvShowPlaylist(tvShow: CatalogueEntity, newState: Boolean) {
//        tvShow.isOnPlaylist = newState
//        mMovieDao.insertNewPlaylistItem(tvShow)
//    }
}

