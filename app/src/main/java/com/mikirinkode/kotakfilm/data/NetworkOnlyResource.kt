package com.mikirinkode.kotakfilm.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.mikirinkode.kotakfilm.data.source.remote.ApiResponse
import com.mikirinkode.kotakfilm.data.source.remote.StatusResponse
import com.mikirinkode.kotakfilm.utils.AppExecutors
import com.mikirinkode.kotakfilm.vo.Resource

abstract class NetworkOnlyResource<ResultType, RequestType>(private val mExecutors: AppExecutors) {
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)

        val apiResponse = createCall()

        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            when (response.status){
                StatusResponse.SUCCESS -> mExecutors.diskIO().execute {
                    mExecutors.mainThread().execute {
                        result.addSource(loadFromNetwork(response.body)) { newData ->
                            result.value = Resource.success(newData)
                        }
                    }
                }
                StatusResponse.EMPTY -> mExecutors.mainThread().execute {
                    result.addSource(loadFromNetwork(response.body)) { newData ->
                        result.value = Resource.success(newData)
                    }
                }
                StatusResponse.ERROR -> {
                    result.addSource(loadFromNetwork(response.body)) { newData ->
                        result.value = Resource.error(response.message, newData)
                    }
                }
            }
        }
    }


    protected abstract fun loadFromNetwork(data: RequestType): LiveData<ResultType>

    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    fun asLiveData(): LiveData<Resource<ResultType>> = result
}