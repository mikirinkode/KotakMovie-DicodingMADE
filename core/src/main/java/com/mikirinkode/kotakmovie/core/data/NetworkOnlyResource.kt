package com.mikirinkode.kotakmovie.core.data

import com.mikirinkode.kotakmovie.core.data.source.remote.ApiResponse
import com.mikirinkode.kotakmovie.core.vo.Resource
import kotlinx.coroutines.flow.*

abstract class NetworkOnlyResource<ResultType, RequestType>{
    private val result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = createCall().first()){
            is ApiResponse.Success -> {
                emitAll(loadFromNetwork(apiResponse.data).map { Resource.Success(it) })
            }
            is ApiResponse.Empty -> {
                emitAll(loadFromNetwork(apiResponse.data).map { Resource.Success(it) })
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }


    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract suspend fun loadFromNetwork(data: RequestType): Flow<ResultType>

    fun asFlow(): Flow<Resource<ResultType>> = result
}