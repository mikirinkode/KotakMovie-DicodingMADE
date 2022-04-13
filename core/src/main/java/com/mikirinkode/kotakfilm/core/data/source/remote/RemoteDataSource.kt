package com.mikirinkode.kotakfilm.core.data.source.remote

import android.util.Log
import com.mikirinkode.kotakfilm.core.data.source.remote.response.*
import com.mikirinkode.kotakfilm.core.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api: ApiService) {

    private val apiKey = Constants.API_KEY

    suspend fun searchMovies(query: String): Flow<ApiResponse<SearchResponse>> {
        return flow {
            try {
                val response = api.searchMovies(apiKey, query)
                val movieList = response.results
                if (movieList.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty(response))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "Failed to Get Search Movie Results")
                Log.e("RemoteDataSource", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getPopularMovieList(): Flow<ApiResponse<MovieListResponse>> {
        return flow {
            try {
                val response = api.getPopularMovieList(apiKey)
                val movieList = response.results
                if (movieList.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty(response))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "Failed to Get Popular Movie List")
                Log.e("RemoteDataSource", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTrendingMovieList(): Flow<ApiResponse<MovieListResponse>> {
        return flow {
            try {
                val response = api.getTrendingMovieList(apiKey)
                val movieList = response.results
                if (movieList.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty(response))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "Failed to Get Trending Movie List")
                Log.e("RemoteDataSource", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUpcomingMovieList(): Flow<ApiResponse<MovieListResponse>> {
        return flow {
            try {
                val response = api.getUpcomingMovieList(apiKey)
                val movieList = response.results
                if (movieList.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty(response))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "Failed to Get Upcoming Movie List")
                Log.e("RemoteDataSource", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieDetail(movieId: Int): Flow<ApiResponse<MovieDetailResponse>> {
        return flow {
            try {
                val response = api.getDetailMovie(movieId, apiKey)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "Failed to Get Movie Detail")
                Log.e("RemoteDataSource", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieTrailer(movieId: Int): Flow<ApiResponse<TrailerVideoResponse>> {
        return flow {
            try {
                val response = api.getMovieTrailer(movieId, apiKey)
                val trailerList = response.results
                if (trailerList.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty(response))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "Failed to Get Movie Trailer Video")
                Log.e("RemoteDataSource", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTvTrailer(tvShowId: Int): Flow<ApiResponse<TrailerVideoResponse>> {
        return flow {
            try {
                val response = api.getTvShowTrailer(tvShowId, apiKey)
                val trailerList = response.results
                if (trailerList.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty(response))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "Failed to Get TV Show Trailer Video")
                Log.e("RemoteDataSource", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getPopularTvShowList(): Flow<ApiResponse<TvShowListResponse>> {
        return flow {
            try {
                val response = api.getPopularTvShowList(apiKey)
                val tvList = response.results
                if (tvList.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty(response))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "Failed to Get Popular TvShow List")
                Log.e("RemoteDataSource", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTopTvShowList(): Flow<ApiResponse<TvShowListResponse>> {
        return flow {
            try {
                val response = api.getTopTvShowList(apiKey)
                val tvList = response.results
                if (tvList.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty(response))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "Failed to Get Top TvShow List")
                Log.e("RemoteDataSource", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }


    suspend fun getTvShowDetail(tvShowId: Int): Flow<ApiResponse<TvShowDetailResponse>> {
        return flow {
            try {
                val response = api.getDetailTvShow(tvShowId, apiKey)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "Failed to Get Tv Show Detail")
                Log.e("RemoteDataSource", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}