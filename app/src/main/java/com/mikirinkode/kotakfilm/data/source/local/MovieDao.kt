package com.mikirinkode.kotakfilm.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.mikirinkode.kotakfilm.data.model.MovieEntity
import com.mikirinkode.kotakfilm.data.model.TvShowEntity

@Dao
interface MovieDao {

    @RawQuery(observedEntities = [MovieEntity::class])
    fun getMovies(query: SupportSQLiteQuery): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM MovieEntities WHERE id = :id")
    fun getMovieDetail(id: Int): LiveData<MovieEntity>

    @Query("SELECT * FROM MovieEntities where isFavorite = 1")
    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieList(movies: List<MovieEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)


    @RawQuery(observedEntities = [MovieEntity::class])
    fun getTvShows(query: SupportSQLiteQuery): DataSource.Factory<Int, TvShowEntity>

    @Query("SELECT * FROM TvShowEntities WHERE id = :id")
    fun getTvShowDetail(id: Int): LiveData<TvShowEntity>

    @Query("SELECT * FROM TvShowEntities where isFavorite = 1")
    fun getFavoriteTvShows(): DataSource.Factory<Int, TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShowList(tvShowList: List<TvShowEntity>)

    @Update
    fun updateTvShow(tvShow: TvShowEntity)


}