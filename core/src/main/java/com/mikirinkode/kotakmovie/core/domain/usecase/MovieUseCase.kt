package com.mikirinkode.kotakmovie.core.domain.usecase

import com.mikirinkode.kotakmovie.core.domain.model.Catalogue
import com.mikirinkode.kotakmovie.core.domain.model.TrailerVideo
import com.mikirinkode.kotakmovie.core.vo.Resource
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun searchMovies(query: String): Flow<Resource<List<Catalogue>>>

    fun getPopularMovies(sort: String): Flow<Resource<List<Catalogue>>>

    fun getUpcomingMovies(): Flow<Resource<List<Catalogue>>>

    fun getTrendingMovies(): Flow<Resource<List<Catalogue>>>

    fun getMovieDetail(movie: Catalogue): Flow<Resource<Catalogue>>

    fun getMoviePlaylist(): Flow<List<Catalogue>>

    fun getMovieTrailer(movie: Catalogue): Flow<Resource<List<TrailerVideo>>>


    suspend fun insertPlaylistItem(item: Catalogue, state: Boolean)

    suspend fun removePlaylistItem(item: Catalogue)


    fun getTvTrailer(tvShow: Catalogue): Flow<Resource<List<TrailerVideo>>>

    fun getPopularTvShows(sort: String): Flow<Resource<List<Catalogue>>>

    fun getTopTvShowList(): Flow<Resource<List<Catalogue>>>

    fun getTvShowDetail(tvShow: Catalogue): Flow<Resource<Catalogue>>

    fun getTvShowPlaylist(): Flow<List<Catalogue>>

}