package com.mikirinkode.kotakfilm.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.mikirinkode.kotakfilm.data.model.MovieEntity
import com.mikirinkode.kotakfilm.data.model.TvShowEntity

@Dao
interface MovieDao {

    @RawQuery(observedEntities = [MovieEntity::class])
    fun getPopularMovies(query: SupportSQLiteQuery): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM MovieEntities WHERE id = :id")
    fun getMovieDetail(id: Int): LiveData<MovieEntity>

    @Query("SELECT * FROM MovieEntities WHERE isUpcoming = 1 ORDER BY releaseDate DESC")
    fun getUpcomingMovies(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM MovieEntities WHERE title LIKE '%' ||:search || '%'")
    fun searchMovies(search: String): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM MovieEntities WHERE isOnTrending = 1")
    fun getTrendingMovies(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM MovieEntities WHERE isFavorite = 1")
    fun getFavoriteMovies(): LiveData<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieList(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertSearchResult(movies: MovieEntity)

    @Update
    fun updateMovie(movie: MovieEntity)


    @RawQuery(observedEntities = [MovieEntity::class])
    fun getPopularTvShows(query: SupportSQLiteQuery): LiveData<List<TvShowEntity>>

    @Query("SELECT * FROM TvShowEntities WHERE id = :id")
    fun getTvShowDetail(id: Int): LiveData<TvShowEntity>

    @Query("SELECT * FROM TvShowEntities WHERE isFavorite = 1")
    fun getFavoriteTvShows(): LiveData<List<TvShowEntity>>

    @Query("SELECT * FROM TvShowEntities WHERE isAiringToday = 1")
    fun getAiringTodayTvShows(): LiveData<List<TvShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShowList(tvShowList: List<TvShowEntity>)

    @Update
    fun updateTvShow(tvShow: TvShowEntity)


}