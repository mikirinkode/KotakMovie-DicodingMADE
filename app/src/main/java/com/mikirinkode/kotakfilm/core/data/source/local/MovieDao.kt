package com.mikirinkode.kotakfilm.core.data.source.local

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.mikirinkode.kotakfilm.core.data.entity.CatalogueEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @RawQuery(observedEntities = [CatalogueEntity::class])
    fun getPopularMovies(query: SupportSQLiteQuery): Flow<List<CatalogueEntity>>

    @Query("SELECT * FROM CatalogueEntities WHERE isOnPlaylist = 1 AND isTvShow = 0")
    fun getMoviePlaylist(): Flow<List<CatalogueEntity>>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatalogueList(catalogueList: List<CatalogueEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewPlaylistItem(catalogueItem: CatalogueEntity)

    @Delete
    suspend fun removePlaylistItem(catalogueItem: CatalogueEntity)


    @RawQuery(observedEntities = [CatalogueEntity::class])
    fun getPopularTvShows(query: SupportSQLiteQuery): Flow<List<CatalogueEntity>>

    @Query("SELECT * FROM CatalogueEntities WHERE isOnPlaylist = 1 AND isTvShow = 1")
    fun getTvShowPlaylist(): Flow<List<CatalogueEntity>>
}