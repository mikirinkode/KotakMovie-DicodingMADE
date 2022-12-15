package com.mikirinkode.kotakmovie.core.data.source

import com.mikirinkode.kotakmovie.core.domain.model.Catalogue
import com.mikirinkode.kotakmovie.core.domain.model.TrailerVideo
import com.mikirinkode.kotakmovie.core.vo.Resource
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun searchMovies(query: String): Flow<Resource<List<Catalogue>>>

    fun getPopularMovies(sort: String, shouldFetchAgain: Boolean): Flow<Resource<List<Catalogue>>>

    fun getUpcomingMovies(): Flow<Resource<List<Catalogue>>>

    fun getTrendingThisWeekList(): Flow<Resource<List<Catalogue>>>

    fun getMovieDetail(movieId: Int): Flow<Resource<Catalogue>>

    fun getMoviePlaylist(): Flow<List<Catalogue>>

    fun getMovieTrailer(movieId: Int): Flow<Resource<List<TrailerVideo>>>


    suspend fun insertPlaylistItem(item: Catalogue, state: Boolean)

    suspend fun removePlaylistItem(item: Catalogue)


    fun getTvTrailer(tvShowId: Int): Flow<Resource<List<TrailerVideo>>>

    fun getPopularTvShows(sort: String, shouldFetchAgain: Boolean): Flow<Resource<List<Catalogue>>>

    fun getTopTvShowList(): Flow<Resource<List<Catalogue>>>

    fun getTvShowDetail(tvShowId: Int): Flow<Resource<Catalogue>>

    fun getTvShowPlaylist(): Flow<List<Catalogue>>
}