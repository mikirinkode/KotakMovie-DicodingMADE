package com.mikirinkode.kotakfilm.core.data

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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : IMovieRepository {


    override fun getPopularMovies(sort: String): Flow<Resource<List<Catalogue>>> {
        return object :
            NetworkBoundResource<List<Catalogue>, MovieListResponse>() {
            override fun loadFromDB(): Flow<List<Catalogue>> {
                return localDataSource.getMovieList(sort).map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Catalogue>?): Boolean {
                return data == null || data.isEmpty() || data.size <= 10
            }

            override suspend fun createCall(): Flow<ApiResponse<MovieListResponse>> {
                return remoteDataSource.getPopularMovieList()
            }

            override suspend fun saveCallResult(data: MovieListResponse) {
                val movieList = DataMapper.mapMovieResponsesToEntities(data)
                localDataSource.insertMovieList(movieList)
            }
        }.asFlow()
    }

    override fun searchMovies(query: String): Flow<Resource<List<Catalogue>>> {
        return object :
            NetworkOnlyResource<List<Catalogue>, MovieListResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<MovieListResponse>> {
                return remoteDataSource.searchMovies(query)
            }

            override suspend fun loadFromNetwork(data: MovieListResponse): Flow<List<Catalogue>> {
                return flowOf(DataMapper.mapMovieResponsesToDomain(data))
            }
        }.asFlow()
    }


    override fun getTrendingMovies(): Flow<Resource<List<Catalogue>>> {
        return object :
            NetworkOnlyResource<List<Catalogue>, MovieListResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<MovieListResponse>> {
                return remoteDataSource.getTrendingMovieList()
            }

            override suspend fun loadFromNetwork(data: MovieListResponse): Flow<List<Catalogue>> {
                return flowOf(DataMapper.mapMovieResponsesToDomain(data))
            }
        }.asFlow()
    }


    override fun getUpcomingMovies(): Flow<Resource<List<Catalogue>>> {
        return object :
            NetworkOnlyResource<List<Catalogue>, MovieListResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<MovieListResponse>> {
                return remoteDataSource.getUpcomingMovieList()
            }

            override suspend fun loadFromNetwork(data: MovieListResponse): Flow<List<Catalogue>> {
                return flowOf(DataMapper.mapMovieResponsesToDomain(data))
            }
        }.asFlow()
    }


    override fun getMovieDetail(movie: Catalogue): Flow<Resource<Catalogue>> {
        return object : NetworkOnlyResource<Catalogue, MovieDetailResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<MovieDetailResponse>> {
                return remoteDataSource.getMovieDetail(movie.id)
            }

            override suspend fun loadFromNetwork(data: MovieDetailResponse): Flow<Catalogue> {
                return flowOf(DataMapper.mapDetailMovieResponseToDomain(data))
            }
        }.asFlow()
    }

    override fun getMovieTrailer(movie: Catalogue): Flow<Resource<List<TrailerVideo>>> {
        return object :
            NetworkOnlyResource<List<TrailerVideo>, TrailerVideoResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<TrailerVideoResponse>> {
                return remoteDataSource.getMovieTrailer(movie.id)
            }

            override suspend fun loadFromNetwork(data: TrailerVideoResponse): Flow<List<TrailerVideo>> {
                return flowOf(DataMapper.mapTrailerResponseToDomain(data))
            }
        }.asFlow()
    }

    override fun getTvTrailer(tvShow: Catalogue): Flow<Resource<List<TrailerVideo>>> {
        return object :
            NetworkOnlyResource<List<TrailerVideo>, TrailerVideoResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<TrailerVideoResponse>> {
                return remoteDataSource.getTvTrailer(tvShow.id)
            }

            override suspend fun loadFromNetwork(data: TrailerVideoResponse): Flow<List<TrailerVideo>> {
                return flowOf(DataMapper.mapTrailerResponseToDomain(data))
            }
        }.asFlow()
    }

    override fun getMoviePlaylist(): Flow<List<Catalogue>> {
        return localDataSource.getMoviePlaylist().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override suspend fun insertPlaylistItem(item: Catalogue, state: Boolean) {
        localDataSource.insertPlaylistItem(
            DataMapper.mapDomainToEntity(item),
            state
        )
    }

    override suspend fun removePlaylistItem(item: Catalogue) {
        localDataSource.removeItemFromPlaylist(DataMapper.mapDomainToEntity(item))
    }

    override fun getPopularTvShows(sort: String): Flow<Resource<List<Catalogue>>> {
        return object :
            NetworkBoundResource<List<Catalogue>, TvShowListResponse>() {
            override fun loadFromDB(): Flow<List<Catalogue>> {
                return localDataSource.getTvShowList(sort).map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Catalogue>?): Boolean {
                return data == null || data.isEmpty() || data.size <= 10
            }

            override suspend fun createCall(): Flow<ApiResponse<TvShowListResponse>> {
                return remoteDataSource.getPopularTvShowList()
            }

            override suspend fun saveCallResult(data: TvShowListResponse) {
                localDataSource.insertTvShowList(DataMapper.mapTvResponsesToEntities(data))
            }
        }.asFlow()
    }

    override fun getTopTvShowList(): Flow<Resource<List<Catalogue>>> {
        return object :
            NetworkOnlyResource<List<Catalogue>, TvShowListResponse>() {
            override suspend fun loadFromNetwork(data: TvShowListResponse): Flow<List<Catalogue>> {
                return flowOf(DataMapper.mapTvResponsesToDomain(data))
            }

            override suspend fun createCall(): Flow<ApiResponse<TvShowListResponse>> {
                return remoteDataSource.getTopTvShowList()
            }

        }.asFlow()
    }


    override fun getTvShowDetail(tvShow: Catalogue): Flow<Resource<Catalogue>> {
        return object : NetworkOnlyResource<Catalogue, TvShowDetailResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<TvShowDetailResponse>> {
                return remoteDataSource.getTvShowDetail(tvShow.id)
            }

            override suspend fun loadFromNetwork(data: TvShowDetailResponse): Flow<Catalogue> {
                return flowOf(DataMapper.mapDetailTvResponseToDomain(data))
            }
        }.asFlow()
    }

    override fun getTvShowPlaylist(): Flow<List<Catalogue>> {
        return localDataSource.getTvShowPlaylist().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localData: LocalDataSource
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteDataSource, localData).apply {
                    instance = this
                }
            }
    }
}