package com.mikirinkode.kotakmovie.core.data.source.local

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.mikirinkode.kotakmovie.core.data.entity.CatalogueEntity
import com.mikirinkode.kotakmovie.core.domain.model.Catalogue
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @RawQuery(observedEntities = [CatalogueEntity::class])
    fun getPopularMovies(query: SupportSQLiteQuery): Flow<List<CatalogueEntity>>

    @Query("SELECT * FROM CatalogueEntities WHERE isOnPlaylist = 1 AND isTvShow = 0")
    fun getMoviePlaylist(): Flow<List<CatalogueEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatalogue(catalogueEntity: CatalogueEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCatalogueList(catalogueList: List<CatalogueEntity>)

    @Query("SELECT * FROM CatalogueEntities WHERE id = :id AND isTvShow = 0")
    fun getMovieDetail(id: Int): Flow<CatalogueEntity>

    @Query("SELECT * FROM CatalogueEntities WHERE isTrending = 1")
    fun getTrendingCatalogue(): Flow<List<CatalogueEntity>>

    @Query("SELECT * FROM CatalogueEntities WHERE isUpcoming = 1")
    fun getUpcomingMovies(): Flow<List<CatalogueEntity>>

    @Query("SELECT * FROM CatalogueEntities WHERE isTopTv = 1 AND isTvShow = 1")
    fun getTopRatedTvShows(): Flow<List<CatalogueEntity>>

    @Query("SELECT * FROM CatalogueEntities WHERE title LIKE '%' || :query || '%'")
    fun searchMovies(query: String): Flow<List<CatalogueEntity>>
    /**
     * Playlist
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewPlaylistItem(catalogueItem: CatalogueEntity)

    @Update
    suspend fun removePlaylistItem(catalogueItem: CatalogueEntity)


    @RawQuery(observedEntities = [CatalogueEntity::class])
    fun getPopularTvShows(query: SupportSQLiteQuery): Flow<List<CatalogueEntity>>

    @Query("SELECT * FROM CatalogueEntities WHERE isOnPlaylist = 1 AND isTvShow = 1")
    fun getTvShowPlaylist(): Flow<List<CatalogueEntity>>


    @Query("SELECT * FROM CatalogueEntities WHERE id = :id AND isTvShow = 1")
    fun getTvShowDetail(id: Int): Flow<CatalogueEntity>

}