package com.mikirinkode.kotakmovie.core.data.source.local

import com.mikirinkode.kotakmovie.core.data.entity.CatalogueEntity
import com.mikirinkode.kotakmovie.core.utils.SortUtils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val mMovieDao: MovieDao) {

    fun getMovieList(sort: String): Flow<List<CatalogueEntity>> = mMovieDao.getPopularMovies(
        SortUtils.getSortedQuery(sort, 0))

    fun getMoviePlaylist(): Flow<List<CatalogueEntity>> = mMovieDao.getMoviePlaylist()

    suspend fun insertMovieList(movies: List<CatalogueEntity>) = mMovieDao.insertCatalogueList(movies)


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

    companion object {
        @Volatile
        private var instance: LocalDataSource? = null

        fun getInstance(dao: MovieDao): LocalDataSource =
            instance ?: synchronized(this) {
                LocalDataSource(dao).apply {
                    instance = this
                }
            }
    }
}

