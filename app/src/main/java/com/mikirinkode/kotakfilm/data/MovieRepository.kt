package com.mikirinkode.kotakfilm.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mikirinkode.kotakfilm.data.model.MovieEntity
import com.mikirinkode.kotakfilm.data.model.TvShowEntity
import com.mikirinkode.kotakfilm.data.source.local.LocalDataSource
import com.mikirinkode.kotakfilm.data.source.remote.ApiResponse
import com.mikirinkode.kotakfilm.data.source.remote.RemoteDataSource
import com.mikirinkode.kotakfilm.data.source.remote.response.MovieDetailResponse
import com.mikirinkode.kotakfilm.data.source.remote.response.MovieListResponse
import com.mikirinkode.kotakfilm.data.source.remote.response.TvShowDetailResponse
import com.mikirinkode.kotakfilm.data.source.remote.response.TvShowListResponse
import com.mikirinkode.kotakfilm.utils.AppExecutors
import com.mikirinkode.kotakfilm.vo.Resource

class MovieRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): MovieDataSource {

    override fun getPopularMoviesList(sort: String): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, MovieListResponse>(appExecutors){
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(5)
                    .setPageSize(5)
                    .build()
                return LivePagedListBuilder(localDataSource.getMovieList(sort), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<MovieListResponse>> {
                return remoteDataSource.getMovieList()
            }

            override fun saveCallResult(movieResponses: MovieListResponse) {
                val movieList = ArrayList<MovieEntity>()
                if (movieResponses != null){
                    movieResponses.results.forEach {
                        val movie = MovieEntity(it.id, it.title, it.releaseDate, it.overview, null,
                            null, null, it.voteAverage, it.posterPath, it.backdropPath)
                        movieList.add(movie)
                    }
                }
                localDataSource.insertMovieList(movieList)
            }
        }.asLiveData()
    }


    override fun getMovieDetail(movieId: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieDetailResponse>(appExecutors){
            override fun loadFromDB(): LiveData<MovieEntity> {
                return localDataSource.getMovieDetail(movieId)
            }

            override fun shouldFetch(data: MovieEntity?): Boolean {
                return data?.genres == null && data?.runtime == null && data?.tagline == null
            }

            override fun createCall(): LiveData<ApiResponse<MovieDetailResponse>> {
                return remoteDataSource.getMovieDetail(movieId)
            }

            override fun saveCallResult(movieResponse: MovieDetailResponse) {
                if (movieResponse != null){
                    movieResponse.apply {
                        val genreList = ArrayList<String>()
                        genres.forEach { genreList.add(it.name) }
                        val genre = genreList.joinToString(separator = ", ")

                        val movieDetail = MovieEntity(id, title, releaseDate, overview, tagline,
                            genre, runtime, voteAverage, posterPath, backdropPath)
                        localDataSource.updateMovieData(movieDetail)
                    }
                }
            }
        }.asLiveData()
    }

    override fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(5)
            .setPageSize(5)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovies(), config).build()
    }

    override fun setFavoriteMovie(movie: MovieEntity, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setFavoriteMovie(movie, state) }
    }


    override fun getPopularTvShowsList(sort: String): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object : NetworkBoundResource<PagedList<TvShowEntity>, TvShowListResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(5)
                    .setPageSize(5)
                    .build()
                return LivePagedListBuilder(localDataSource.getTvShowList(sort), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<TvShowListResponse>> {
                return remoteDataSource.getTvShowList()
            }

            override fun saveCallResult(tvShowResponses: TvShowListResponse) {
                val tvShowList = ArrayList<TvShowEntity>()
                if (tvShowResponses != null) {
                    tvShowResponses.results.forEach {
                        val tvShow = TvShowEntity(
                            it.id, it.name, it.firstAirDate, it.overview, null,
                            null, null, it.voteAverage, it.posterPath, it.backdropPath
                        )
                        tvShowList.add(tvShow)
                    }
                }
                localDataSource.insertTvShowList(tvShowList)
            }
        }.asLiveData()
    }


    override fun getTvShowDetail(tvShowId: Int): LiveData<Resource<TvShowEntity>> {
        return object: NetworkBoundResource<TvShowEntity, TvShowDetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<TvShowEntity> {
                return localDataSource.getTvShowDetail(tvShowId)
            }

            override fun shouldFetch(data: TvShowEntity?): Boolean {
                return data?.genres == null && data?.runtime == null && data?.tagline == null
            }

            override fun createCall(): LiveData<ApiResponse<TvShowDetailResponse>> {
                return remoteDataSource.getTvShowDetail(tvShowId)
            }

            override fun saveCallResult(tvShowResponse: TvShowDetailResponse) {
                if(tvShowResponse != null){
                    tvShowResponse.apply {
                        val genreList = ArrayList<String>()
                        genres.forEach { genreList.add(it.name) }
                        val genre = genreList.joinToString(separator = ", ")

                        val tvShowDetail = TvShowEntity(id, name, firstAirDate, overview, tagline,
                            genre, if(episodeRunTime.isEmpty()) 0 else episodeRunTime[0], voteAverage, posterPath, backdropPath)
                        localDataSource.updateTvShowData(tvShowDetail)
                    }
                }
            }
        }.asLiveData()
    }

    override fun getFavoriteTvShows(): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(5)
            .setPageSize(5)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteTvShows(), config).build()
    }

    override fun setFavoriteTvShow(tvShow: TvShowEntity, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setFavoriteTvShow(tvShow, state) }
    }


    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource, localData: LocalDataSource, appExecutors: AppExecutors): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteDataSource, localData, appExecutors).apply {
                    instance = this
                }
            }
    }
}