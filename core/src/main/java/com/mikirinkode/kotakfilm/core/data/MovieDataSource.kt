package com.mikirinkode.kotakfilm.core.data

import androidx.lifecycle.LiveData
import com.mikirinkode.kotakfilm.core.domain.model.Catalogue
import com.mikirinkode.kotakfilm.core.domain.model.TrailerVideo
import com.mikirinkode.kotakfilm.core.vo.Resource

interface MovieDataSource {

    fun searchMovies(query: String): LiveData<Resource<List<Catalogue>>>

    fun getPopularMovies(sort: String): LiveData<Resource<List<Catalogue>>>

    fun getUpcomingMovies(): LiveData<Resource<List<Catalogue>>>

    fun getTrendingMovies(): LiveData<Resource<List<Catalogue>>>

    fun getMovieDetail(movie: Catalogue): LiveData<Resource<Catalogue>>

    fun getMoviePlaylist(): LiveData<List<Catalogue>>

    fun setMoviePlaylist(movie: Catalogue, state: Boolean)

    fun getMovieTrailer(movie: Catalogue): LiveData<Resource<TrailerVideo>>


    fun getTvTrailer(tvShow: Catalogue): LiveData<Resource<TrailerVideo>>

    fun getPopularTvShows(sort: String): LiveData<Resource<List<Catalogue>>>

    fun getTopTvShowList(): LiveData<Resource<List<Catalogue>>>

    fun getTvShowDetail(tvShow: Catalogue): LiveData<Resource<Catalogue>>

    fun getTvShowPlaylist(): LiveData<List<Catalogue>>

    fun setTvShowPlaylist(tvShow: Catalogue, state: Boolean)
}