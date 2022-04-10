package com.mikirinkode.kotakfilm.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.mikirinkode.kotakfilm.core.data.source.local.LocalDataSource
import com.mikirinkode.kotakfilm.core.data.source.remote.ApiResponse
import com.mikirinkode.kotakfilm.core.data.source.remote.RemoteDataSource
import com.mikirinkode.kotakfilm.core.data.source.remote.response.*
import com.mikirinkode.kotakfilm.core.domain.model.Catalogue
import com.mikirinkode.kotakfilm.core.domain.model.TrailerVideo
import com.mikirinkode.kotakfilm.core.domain.repository.IMovieRepository
import com.mikirinkode.kotakfilm.core.utils.AppExecutors
import com.mikirinkode.kotakfilm.core.utils.DataMapper
import com.mikirinkode.kotakfilm.core.vo.Resource

class MovieRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieRepository {


    override fun getPopularMovies(sort: String): LiveData<Resource<List<Catalogue>>> {
        return object :
            NetworkBoundResource<List<Catalogue>, MovieListResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<List<Catalogue>> {
                return Transformations.map(localDataSource.getMovieList(sort)) {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Catalogue>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<MovieListResponse>> {
                return remoteDataSource.getPopularMovieList()
            }

            override fun saveCallResult(data: MovieListResponse) {
                val movieList = DataMapper.mapMovieResponsesToEntities(data)
                localDataSource.insertMovieList(movieList)
            }
        }.asLiveData()
    }

    override fun searchMovies(query: String): LiveData<Resource<List<Catalogue>>> {
        return object :
            NetworkOnlyResource<List<Catalogue>, MovieListResponse>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<MovieListResponse>> {
                return remoteDataSource.searchMovies(query)
            }

            override fun loadFromNetwork(data: MovieListResponse): LiveData<List<Catalogue>> {
                val movieListResult = MutableLiveData<List<Catalogue>>()
                movieListResult.postValue(DataMapper.mapMovieResponsesToDomain(data))
                return movieListResult
            }
        }.asLiveData()
    }


    override fun getTrendingMovies(): LiveData<Resource<List<Catalogue>>> {
        return object :
            NetworkOnlyResource<List<Catalogue>, MovieListResponse>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<MovieListResponse>> {
                return remoteDataSource.getTrendingMovieList()
            }

            override fun loadFromNetwork(data: MovieListResponse): LiveData<List<Catalogue>> {
                val movieListResult = MutableLiveData<List<Catalogue>>()
                movieListResult.postValue(DataMapper.mapMovieResponsesToDomain(data))
                return movieListResult
            }
        }.asLiveData()
    }


    override fun getUpcomingMovies(): LiveData<Resource<List<Catalogue>>> {
        return object :
            NetworkOnlyResource<List<Catalogue>, MovieListResponse>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<MovieListResponse>> {
                return remoteDataSource.getUpcomingMovieList()
            }

            override fun loadFromNetwork(data: MovieListResponse): LiveData<List<Catalogue>> {
                val movieListResult = MutableLiveData<List<Catalogue>>()
                movieListResult.postValue(DataMapper.mapMovieResponsesToDomain(data))
                return movieListResult
            }
        }.asLiveData()
    }


    override fun getMovieDetail(movie: Catalogue): LiveData<Resource<Catalogue>> {
        return object : NetworkBoundResource<Catalogue, MovieDetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<Catalogue> {
                return Transformations.map(localDataSource.getMovieDetail(movie.id)) {
                    DataMapper.mapEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: Catalogue?): Boolean {
                return data?.genres == null || data.runtime == null && data.tagline == null
            }

            override fun createCall(): LiveData<ApiResponse<MovieDetailResponse>> {
                return remoteDataSource.getMovieDetail(movie.id)
            }

            override fun saveCallResult(data: MovieDetailResponse) {
                localDataSource.updateMovieData(DataMapper.mapDetailMovieResponseToEntity(data))
            }
        }.asLiveData()
    }

    override fun getMovieTrailer(movie: Catalogue): LiveData<Resource<TrailerVideo>> {
        return object :
            NetworkOnlyResource<TrailerVideo, TrailerVideoResponse>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<TrailerVideoResponse>> {
                return remoteDataSource.getMovieTrailer(movie.id)
            }

            override fun loadFromNetwork(data: TrailerVideoResponse): LiveData<TrailerVideo> {
                val movieTrailer = MutableLiveData<TrailerVideo>()
                data.results.forEach {
                    if (it.site == "YouTube" && it.type == "Trailer") {
                        movieTrailer.postValue(DataMapper.mapTrailerResponseToDomain(it))
                    }
                }
                return movieTrailer
            }
        }.asLiveData()
    }

    override fun getTvTrailer(tvShow: Catalogue): LiveData<Resource<TrailerVideo>> {
        return object :
            NetworkOnlyResource<TrailerVideo, TrailerVideoResponse>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<TrailerVideoResponse>> {
                return remoteDataSource.getTvTrailer(tvShow.id)
            }

            override fun loadFromNetwork(data: TrailerVideoResponse): LiveData<TrailerVideo> {
                val tvShowTrailer = MutableLiveData<TrailerVideo>()
                data.results.forEach {
                    if (it.site == "YouTube" && it.type == "Trailer") {
                        tvShowTrailer.postValue(DataMapper.mapTrailerResponseToDomain(it))
                    }
                }
                return tvShowTrailer
            }
        }.asLiveData()
    }

    override fun getMoviePlaylist(): LiveData<List<Catalogue>> {
        return Transformations.map(localDataSource.getMoviePlaylist()) {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setMoviePlaylist(movie: Catalogue, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setMoviePlaylist(
                DataMapper.mapDomainToEntity(movie),
                state
            )
        }
    }

    override fun getPopularTvShows(sort: String): LiveData<Resource<List<Catalogue>>> {
        return object :
            NetworkBoundResource<List<Catalogue>, TvShowListResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<List<Catalogue>> {
                return Transformations.map(localDataSource.getTvShowList(sort)) {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Catalogue>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<TvShowListResponse>> {
                return remoteDataSource.getPopularTvShowList()
            }

            override fun saveCallResult(data: TvShowListResponse) {
                localDataSource.insertTvShowList(DataMapper.mapTvResponsesToEntities(data))
            }
        }.asLiveData()
    }

    override fun getTopTvShowList(): LiveData<Resource<List<Catalogue>>> {
        return object :
            NetworkOnlyResource<List<Catalogue>, TvShowListResponse>(appExecutors) {
            override fun loadFromNetwork(data: TvShowListResponse): LiveData<List<Catalogue>> {
                val tvShowResult = MutableLiveData<List<Catalogue>>()
                tvShowResult.postValue(DataMapper.mapTvResponsesToDomain(data))
                return tvShowResult
            }

            override fun createCall(): LiveData<ApiResponse<TvShowListResponse>> {
                return remoteDataSource.getTopTvShowList()
            }

        }.asLiveData()
    }


    override fun getTvShowDetail(tvShow: Catalogue): LiveData<Resource<Catalogue>> {
        return object : NetworkBoundResource<Catalogue, TvShowDetailResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<Catalogue> {
                return Transformations.map(localDataSource.getTvShowDetail(tvShow.id)) {
                    DataMapper.mapEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: Catalogue?): Boolean {
                return data?.genres == null || data.runtime == null && data.tagline == null
            }

            override fun createCall(): LiveData<ApiResponse<TvShowDetailResponse>> {
                return remoteDataSource.getTvShowDetail(tvShow.id)
            }

            override fun saveCallResult(data: TvShowDetailResponse) {
                localDataSource.updateTvShowData(DataMapper.mapDetailTvResponseToEntity(data))
            }
        }.asLiveData()
    }

    override fun getTvShowPlaylist(): LiveData<List<Catalogue>> {
        return Transformations.map(localDataSource.getTvShowPlaylist()) {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setTvShowPlaylist(tvShow: Catalogue, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setTvShowPlaylist(
                DataMapper.mapDomainToEntity(tvShow),
                state
            )
        }
    }


    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteDataSource, localData, appExecutors).apply {
                    instance = this
                }
            }
    }
}