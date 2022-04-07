package com.mikirinkode.kotakfilm.data

import androidx.lifecycle.LiveData
import com.mikirinkode.kotakfilm.data.model.CatalogueEntity
import com.mikirinkode.kotakfilm.data.model.TrailerVideoEntity
import com.mikirinkode.kotakfilm.vo.Resource

interface MovieDataSource {

    fun searchMovies(query: String): LiveData<Resource<List<CatalogueEntity>>>

    fun getPopularMovies(sort: String): LiveData<Resource<List<CatalogueEntity>>>

    fun getUpcomingMovies(): LiveData<Resource<List<CatalogueEntity>>>

    fun getTrendingMovies(): LiveData<Resource<List<CatalogueEntity>>>

    fun getMovieDetail(movie: CatalogueEntity): LiveData<Resource<CatalogueEntity>>

    fun getMoviePlaylist(): LiveData<List<CatalogueEntity>>

    fun setMoviePlaylist(movie: CatalogueEntity, state: Boolean)

    fun getMovieTrailer(movie: CatalogueEntity): LiveData<Resource<List<TrailerVideoEntity>>>


    fun getTvTrailer(tvShow: CatalogueEntity): LiveData<Resource<List<TrailerVideoEntity>>>

    fun getPopularTvShows(sort: String): LiveData<Resource<List<CatalogueEntity>>>

    fun getTopTvShowList(): LiveData<Resource<List<CatalogueEntity>>>

    fun getTvShowDetail(tvShow: CatalogueEntity): LiveData<Resource<CatalogueEntity>>

    fun getTvShowPlaylist(): LiveData<List<CatalogueEntity>>

    fun setTvShowPlaylist(tvShow: CatalogueEntity, state: Boolean)
}