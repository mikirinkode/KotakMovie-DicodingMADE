package com.mikirinkode.kotakfilm.data

import androidx.lifecycle.LiveData
import com.mikirinkode.kotakfilm.data.model.MovieEntity
import com.mikirinkode.kotakfilm.data.model.TrailerVideoEntity
import com.mikirinkode.kotakfilm.data.model.TvShowEntity
import com.mikirinkode.kotakfilm.vo.Resource

interface MovieDataSource {

    fun searchMovies(query: String): LiveData<Resource<List<MovieEntity>>>

    fun getPopularMovies(sort: String): LiveData<Resource<List<MovieEntity>>>

    fun getUpcomingMovies(): LiveData<Resource<List<MovieEntity>>>

    fun getTrendingMovies(): LiveData<Resource<List<MovieEntity>>>

    fun getMovieDetail(movie: MovieEntity): LiveData<Resource<MovieEntity>>

    fun getMoviePlaylist(): LiveData<List<MovieEntity>>

    fun setMoviePlaylist(movie: MovieEntity, state: Boolean)

    fun getMovieTrailer(movie: MovieEntity): LiveData<Resource<List<TrailerVideoEntity>>>


    fun getTvTrailer(tvShow: TvShowEntity): LiveData<Resource<List<TrailerVideoEntity>>>

    fun getPopularTvShows(sort: String): LiveData<Resource<List<TvShowEntity>>>

    fun getTopTvShowList(): LiveData<Resource<List<TvShowEntity>>>

    fun getTvShowDetail(tvShow: TvShowEntity): LiveData<Resource<TvShowEntity>>

    fun getTvShowPlaylist(): LiveData<List<TvShowEntity>>

    fun setTvShowPlaylist(tvShow: TvShowEntity, state: Boolean)
}