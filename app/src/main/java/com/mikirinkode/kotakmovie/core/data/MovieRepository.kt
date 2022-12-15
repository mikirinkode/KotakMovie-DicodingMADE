package com.mikirinkode.kotakmovie.core.data

import android.util.Log
import com.mikirinkode.kotakmovie.core.data.entity.CatalogueEntity
import com.mikirinkode.kotakmovie.core.data.source.IMovieRepository
import com.mikirinkode.kotakmovie.core.data.source.local.LocalDataSource
import com.mikirinkode.kotakmovie.core.data.source.remote.ApiResponse
import com.mikirinkode.kotakmovie.core.data.source.remote.RemoteDataSource
import com.mikirinkode.kotakmovie.core.data.source.remote.response.*
import com.mikirinkode.kotakmovie.core.domain.model.Catalogue
import com.mikirinkode.kotakmovie.core.domain.model.TrailerVideo
import com.mikirinkode.kotakmovie.core.utils.DataMapper
import com.mikirinkode.kotakmovie.core.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : IMovieRepository {

    override fun getPopularMovies(
        sort: String,
        shouldFetchAgain: Boolean
    ): Flow<Resource<List<Catalogue>>> {
        return object :
            NetworkBoundResource<List<Catalogue>, MovieListResponse>() {
            override fun loadFromDB(): Flow<List<Catalogue>> {
                return localDataSource.getMovieList(sort).map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Catalogue>?): Boolean {
                return data == null || data.isEmpty() || data.size <= 10 || shouldFetchAgain
            }

            override suspend fun createCall(): Flow<ApiResponse<MovieListResponse>> {
                return remoteDataSource.getPopularMovieList()
            }

            override suspend fun saveCallResult(data: MovieListResponse) {
                val movieList = DataMapper.mapMovieResponsesToEntities(data)
                localDataSource.insertCatalogueList(movieList)
            }
        }.asFlow()
    }

    override fun searchMovies(query: String): Flow<Resource<List<Catalogue>>> {
        return object: NetworkBoundResource<List<Catalogue>, MultiResponse>(){
            override fun loadFromDB(): Flow<List<Catalogue>> {
                return localDataSource.searchMovies(query).map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Catalogue>?): Boolean {
                return data == null || data.isEmpty() || data.size <= 5
            }

            override suspend fun createCall(): Flow<ApiResponse<MultiResponse>> {
                return remoteDataSource.searchMovies(query)
            }

            override suspend fun saveCallResult(data: MultiResponse) {
                val list = DataMapper.mapMultiResponsesToEntities(data)
                localDataSource.insertCatalogueList(list)
            }

        }.asFlow()
    }


    override fun getTrendingThisWeekList(): Flow<Resource<List<Catalogue>>> {
        return object: NetworkBoundResource<List<Catalogue>, MultiResponse>(){
            override fun loadFromDB(): Flow<List<Catalogue>> {
                return localDataSource.getTrendingCatalogue().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Catalogue>?): Boolean {
                return data == null || data.isEmpty() || data.size <= 10
            }

            override suspend fun createCall(): Flow<ApiResponse<MultiResponse>> {
                return remoteDataSource.getTrendingThisWeekList()
            }

            override suspend fun saveCallResult(data: MultiResponse) {
                val list = DataMapper.mapTrendingToEntities(data)
                localDataSource.insertCatalogueList(list)
            }

        }.asFlow()
    }


    override fun getUpcomingMovies(): Flow<Resource<List<Catalogue>>> {
        return object: NetworkBoundResource<List<Catalogue>, MovieListResponse>(){
            override fun loadFromDB(): Flow<List<Catalogue>> {
                return localDataSource.getUpcomingMovies().map {
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
                val movieList = DataMapper.mapUpcomingToEntities(data)
                localDataSource.insertCatalogueList(movieList)
            }

        }.asFlow()
    }


    override fun getMovieDetail(movieId: Int): Flow<Resource<Catalogue>> {
        return object : NetworkBoundResource<Catalogue, MovieDetailResponse>() {
            override fun loadFromDB(): Flow<Catalogue> {
                Log.e("Repo-MovieDetail", "loadFromDB Called, id: $movieId")
                return localDataSource.getMovieDetail(movieId)
                    .map { DataMapper.mapEntityToDomain(it) }
            }

            override fun shouldFetch(data: Catalogue?): Boolean {
                Log.e("Repo-MovieDetail", "shouldFetch Called, id: $movieId")
                return data == null || data.tagline == null || data.genres == null || data.runtime == null
            }

            override suspend fun createCall(): Flow<ApiResponse<MovieDetailResponse>> {
                Log.e("Repo-MovieDetail", "shouldFetch Called, id: $movieId")
                return remoteDataSource.getMovieDetail(movieId)
            }

            override suspend fun saveCallResult(data: MovieDetailResponse) {
                val movie = DataMapper.mapMovieDetailResponseToEntity(data)
                localDataSource.insertCatalogue(movie)
            }

        }.asFlow()
    }

    override fun getMovieTrailer(movieId: Int): Flow<Resource<List<TrailerVideo>>> {
        return object :
            NetworkOnlyResource<List<TrailerVideo>, TrailerVideoResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<TrailerVideoResponse>> {
                return remoteDataSource.getMovieTrailer(movieId)
            }

            override suspend fun loadFromNetwork(data: TrailerVideoResponse): Flow<List<TrailerVideo>> {
                return flowOf(DataMapper.mapTrailerResponseToDomain(data))
            }
        }.asFlow()
    }

    override fun getTvTrailer(tvShowId: Int): Flow<Resource<List<TrailerVideo>>> {
        return object :
            NetworkOnlyResource<List<TrailerVideo>, TrailerVideoResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<TrailerVideoResponse>> {
                return remoteDataSource.getTvTrailer(tvShowId)
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

    override fun getPopularTvShows(
        sort: String,
        shouldFetchAgain: Boolean
    ): Flow<Resource<List<Catalogue>>> {
        return object :
            NetworkBoundResource<List<Catalogue>, TvShowListResponse>() {
            override fun loadFromDB(): Flow<List<Catalogue>> {
                return localDataSource.getTvShowList(sort).map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Catalogue>?): Boolean {
                return data == null || data.isEmpty() || data.size <= 10 || shouldFetchAgain
            }

            override suspend fun createCall(): Flow<ApiResponse<TvShowListResponse>> {
                return remoteDataSource.getPopularTvShowList()
            }

            override suspend fun saveCallResult(data: TvShowListResponse) {
                localDataSource.insertCatalogueList(DataMapper.mapTvResponsesToEntities(data))
            }
        }.asFlow()
    }

    override fun getTopTvShowList(): Flow<Resource<List<Catalogue>>> {
        return object :
            NetworkBoundResource<List<Catalogue>, TvShowListResponse>() {
            override fun loadFromDB(): Flow<List<Catalogue>> {
                return localDataSource.getTopRatedTvShows().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Catalogue>?): Boolean {
                return data == null || data.isEmpty() || data.size <= 10
            }

            override suspend fun createCall(): Flow<ApiResponse<TvShowListResponse>> {
                return remoteDataSource.getTopTvShowList()
            }

            override suspend fun saveCallResult(data: TvShowListResponse) {
                val movieList = DataMapper.mapTopRatedTvToEntities(data)
                localDataSource.insertCatalogueList(movieList)
            }

        }.asFlow()
    }


    override fun getTvShowDetail(tvShowId: Int): Flow<Resource<Catalogue>> {
        return object : NetworkBoundResource<Catalogue, TvShowDetailResponse>() {
            override fun loadFromDB(): Flow<Catalogue> {
                return localDataSource.getTvShowDetail(tvShowId)
                    .map { DataMapper.mapEntityToDomain(it) }
            }

            override fun shouldFetch(data: Catalogue?): Boolean {
                return data == null || data.tagline == null || data.genres == null || data.runtime == null
            }

            override suspend fun createCall(): Flow<ApiResponse<TvShowDetailResponse>> {
                return remoteDataSource.getTvShowDetail(tvShowId)
            }

            override suspend fun saveCallResult(data: TvShowDetailResponse) {
                val movie = DataMapper.mapDetailTvResponseToEntity(data)
                localDataSource.insertCatalogue(movie)
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
        private var instance: IMovieRepository? = null

        fun getInstance(remote: RemoteDataSource, local: LocalDataSource): IMovieRepository =
            instance ?: synchronized(this) {
                MovieRepository(remote, local).apply {
                    instance = this
                }
            }
    }
}