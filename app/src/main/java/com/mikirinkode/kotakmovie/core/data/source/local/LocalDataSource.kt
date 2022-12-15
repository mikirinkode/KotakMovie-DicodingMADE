package com.mikirinkode.kotakmovie.core.data.source.local

import com.mikirinkode.kotakmovie.core.data.entity.CatalogueEntity
import com.mikirinkode.kotakmovie.core.utils.SortUtils
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val mMovieDao: MovieDao) {

    fun getMovieList(sort: String): Flow<List<CatalogueEntity>> = mMovieDao.getPopularMovies(
        SortUtils.getSortedQuery(sort, 0))

    fun getMoviePlaylist(): Flow<List<CatalogueEntity>> = mMovieDao.getMoviePlaylist()

    fun getTrendingCatalogue(): Flow<List<CatalogueEntity>> = mMovieDao.getTrendingCatalogue()

    fun getUpcomingMovies(): Flow<List<CatalogueEntity>> = mMovieDao.getUpcomingMovies()

    fun getTopRatedTvShows(): Flow<List<CatalogueEntity>> = mMovieDao.getTopRatedTvShows()

    fun searchMovies(query: String): Flow<List<CatalogueEntity>> = mMovieDao.searchMovies(query)

    suspend fun insertCatalogueList(movies: List<CatalogueEntity>) = mMovieDao.insertCatalogueList(movies)


    suspend fun insertPlaylistItem(item: CatalogueEntity, newState: Boolean) {
        item.isOnPlaylist = newState
        mMovieDao.insertNewPlaylistItem(item)
    }

    suspend fun removeItemFromPlaylist(item: CatalogueEntity){
        item.isOnPlaylist = false
        mMovieDao.removePlaylistItem(item)
    }

    suspend fun insertCatalogue(catalogueEntity: CatalogueEntity){
        mMovieDao.insertCatalogue(catalogueEntity)
    }

    fun getMovieDetail(id: Int): Flow<CatalogueEntity>{
        return mMovieDao.getMovieDetail(id)
    }

    fun getTvShowDetail(id: Int): Flow<CatalogueEntity>{
        return mMovieDao.getTvShowDetail(id)
    }


    fun getTvShowList(sort: String): Flow<List<CatalogueEntity>> = mMovieDao.getPopularTvShows(
        SortUtils.getSortedQuery(sort, 1))

    fun getTvShowPlaylist(): Flow<List<CatalogueEntity>> = mMovieDao.getTvShowPlaylist()


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

