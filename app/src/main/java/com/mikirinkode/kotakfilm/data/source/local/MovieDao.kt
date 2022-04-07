package com.mikirinkode.kotakfilm.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.mikirinkode.kotakfilm.data.model.CatalogueEntity
import com.mikirinkode.kotakfilm.data.model.TrailerVideoEntity

@Dao
interface MovieDao {

    @RawQuery(observedEntities = [CatalogueEntity::class])
    fun getPopularMovies(query: SupportSQLiteQuery): LiveData<List<CatalogueEntity>>

    @Query("SELECT * FROM CatalogueEntities WHERE id = :id")
    fun getMovieDetail(id: Int): LiveData<CatalogueEntity>

    @Query("SELECT * FROM CatalogueEntities WHERE isUpcoming = 1 ORDER BY releaseDate DESC")
    fun getUpcomingMovies(): LiveData<List<CatalogueEntity>>

    @Query("SELECT * FROM CatalogueEntities WHERE title LIKE '%' ||:search || '%'")
    fun searchMovies(search: String): LiveData<List<CatalogueEntity>>

    @Query("SELECT * FROM CatalogueEntities WHERE isOnTrending = 1")
    fun getTrendingMovies(): LiveData<List<CatalogueEntity>>

    @Query("SELECT * FROM CatalogueEntities WHERE isOnPlaylist = 1")
    fun getMoviePlaylist(): LiveData<List<CatalogueEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieList(movies: List<CatalogueEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSearchResult(movies: CatalogueEntity)

    @Update
    fun updateMovie(movie: CatalogueEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVideoTrailer(trailer: TrailerVideoEntity)

    @Query("SELECT * FROM TrailerVideoEntities WHERE catalogueId = :catalogueId")
    fun getVideoTrailer(catalogueId: Int): LiveData<List<TrailerVideoEntity>>


    @RawQuery(observedEntities = [CatalogueEntity::class])
    fun getPopularTvShows(query: SupportSQLiteQuery): LiveData<List<CatalogueEntity>>

    @Query("SELECT * FROM CatalogueEntities WHERE id = :id AND isTvShow = 1")
    fun getTvShowDetail(id: Int): LiveData<CatalogueEntity>

    @Query("SELECT * FROM CatalogueEntities WHERE isOnPlaylist = 1 AND isTvShow = 1")
    fun getTvShowPlaylist(): LiveData<List<CatalogueEntity>>

    @Query("SELECT * FROM CatalogueEntities WHERE isTopRated = 1 AND isTvShow = 1 ORDER BY voteAverage DESC")
    fun getTopTvShowList(): LiveData<List<CatalogueEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShowList(tvShowList: List<CatalogueEntity>)

    @Update
    fun updateTvShow(tvShow: CatalogueEntity)
}