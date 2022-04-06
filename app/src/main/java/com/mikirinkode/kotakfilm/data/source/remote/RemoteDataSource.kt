package com.mikirinkode.kotakfilm.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mikirinkode.kotakfilm.data.source.remote.response.*
import com.mikirinkode.kotakfilm.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val api: ApiService){

    private val apiKey = Constants.API_KEY

    fun searchMovies(query: String): LiveData<ApiResponse<MovieListResponse>>{
        val movieListResult = MutableLiveData<ApiResponse<MovieListResponse>>()
        api.searchMovies(apiKey, query).enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                if(response.isSuccessful){
                    movieListResult.value = response.body()?.let { ApiResponse.success(it) }
                }
            }
            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "Failed to Get Search Movie Results", t)
                Log.e("RemoteDataSource", t.message.toString())
            }
        })
        return movieListResult
    }

    fun getPopularMovieList(): LiveData<ApiResponse<MovieListResponse>>{
        val movieListResult = MutableLiveData<ApiResponse<MovieListResponse>>()
        api.getPopularMovieList(apiKey).enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                if(response.isSuccessful){
                    movieListResult.value = response.body()?.let { ApiResponse.success(it) }
                }
            }
            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "Failed to Get Popular Movie List", t)
                Log.e("RemoteDataSource", t.message.toString())
            }
        })
        return movieListResult
    }

    fun getTrendingMovieList(): LiveData<ApiResponse<MovieListResponse>>{
        val movieListResult = MutableLiveData<ApiResponse<MovieListResponse>>()
        api.getTrendingMovieList(apiKey).enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                if(response.isSuccessful){
                    movieListResult.value = response.body()?.let { ApiResponse.success(it) }
                }
            }
            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "Failed to Get Trending Movie List", t)
                Log.e("RemoteDataSource", t.message.toString())
            }
        })
        return movieListResult
    }

    fun getUpcomingMovieList(): LiveData<ApiResponse<MovieListResponse>>{
        val movieListResult = MutableLiveData<ApiResponse<MovieListResponse>>()
        api.getUpcomingMovieList(apiKey).enqueue(object : Callback<MovieListResponse> {
            override fun onResponse(
                call: Call<MovieListResponse>,
                response: Response<MovieListResponse>
            ) {
                if(response.isSuccessful){
                    movieListResult.value = response.body()?.let { ApiResponse.success(it) }
                }
            }
            override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "Failed to Get Upcoming Movie List", t)
                Log.e("RemoteDataSource", t.message.toString())
            }
        })
        return movieListResult
    }

    fun getMovieDetail(movieId: Int): LiveData<ApiResponse<MovieDetailResponse>>{
        val movieResult = MutableLiveData<ApiResponse<MovieDetailResponse>>()

        api.getDetailMovie(movieId, apiKey).enqueue(object : Callback<MovieDetailResponse>{
            override fun onResponse(
                call: Call<MovieDetailResponse>,
                response: Response<MovieDetailResponse>
            ) {
                if (response.isSuccessful){
                    movieResult.value = response.body()?.let { ApiResponse.success(it) }
                }
            }
            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "Failed to Get Movie Detail", t)
                Log.e("RemoteDataSource", t.message.toString())
            }
        })
        return movieResult
    }

    fun getMovieTrailer(movieId: Int): LiveData<ApiResponse<TrailerVideoResponse>>{
        val trailerResult = MutableLiveData<ApiResponse<TrailerVideoResponse>>()
        api.getMovieTrailer(movieId, apiKey).enqueue(object : Callback<TrailerVideoResponse>{
            override fun onResponse(
                call: Call<TrailerVideoResponse>,
                response: Response<TrailerVideoResponse>
            ) {
                if (response.isSuccessful){
                    trailerResult.value = response.body()?.let { ApiResponse.success(it) }
                }
            }
            override fun onFailure(call: Call<TrailerVideoResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "Failed to Get Movie Trailer Video", t)
                Log.e("RemoteDataSource", t.message.toString())
            }
        })
        return trailerResult
    }

    fun getTvTrailer(tvShowId: Int): LiveData<ApiResponse<TrailerVideoResponse>>{
        val trailerResult = MutableLiveData<ApiResponse<TrailerVideoResponse>>()
        api.getTvShowTrailer(tvShowId, apiKey).enqueue(object : Callback<TrailerVideoResponse>{
            override fun onResponse(
                call: Call<TrailerVideoResponse>,
                response: Response<TrailerVideoResponse>
            ) {
                if (response.isSuccessful){
                    trailerResult.value = response.body()?.let { ApiResponse.success(it) }
                }
            }
            override fun onFailure(call: Call<TrailerVideoResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "Failed to Get TV Show Trailer Video", t)
                Log.e("RemoteDataSource", t.message.toString())
            }
        })
        return trailerResult
    }

    fun getPopularTvShowList(): LiveData<ApiResponse<TvShowListResponse>>{
        val tvShowListResult = MutableLiveData<ApiResponse<TvShowListResponse>>()
        api.getPopularTvShowList(apiKey).enqueue(object : Callback<TvShowListResponse> {
            override fun onResponse(
                call: Call<TvShowListResponse>,
                response: Response<TvShowListResponse>
            ) {
                if(response.isSuccessful){
                    tvShowListResult.value = response.body()?.let { ApiResponse.success(it) }
                }
            }
            override fun onFailure(call: Call<TvShowListResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "Failed to Get Popular TvShow List", t)
                Log.e("RemoteDataSource", t.message.toString())
            }
        })
        return tvShowListResult
    }

    fun getTopTvShowList(): LiveData<ApiResponse<TvShowListResponse>>{
        val tvShowListResult = MutableLiveData<ApiResponse<TvShowListResponse>>()
        api.getTopTvShowList(apiKey).enqueue(object : Callback<TvShowListResponse> {
            override fun onResponse(
                call: Call<TvShowListResponse>,
                response: Response<TvShowListResponse>
            ) {
                if(response.isSuccessful){
                    tvShowListResult.value = response.body()?.let { ApiResponse.success(it) }
                }
            }
            override fun onFailure(call: Call<TvShowListResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "Failed to Get TvShow List that Airing Today", t)
                Log.e("RemoteDataSource", t.message.toString())
            }
        })
        return tvShowListResult
    }


    fun getTvShowDetail(tvShowId: Int): LiveData<ApiResponse<TvShowDetailResponse>>{
        val tvShowResult = MutableLiveData<ApiResponse<TvShowDetailResponse>>()
        api.getDetailTvShow(tvShowId, apiKey).enqueue(object : Callback<TvShowDetailResponse>{
            override fun onResponse(
                call: Call<TvShowDetailResponse>,
                response: Response<TvShowDetailResponse>
            ) {
                if(response.isSuccessful){
                    tvShowResult.value = response.body()?.let { ApiResponse.success(it) }
                }
            }

            override fun onFailure(call: Call<TvShowDetailResponse>, t: Throwable) {
                Log.e("RemoteDataSource", "Failed to Get Tv Show Detail", t)
                Log.e("RemoteDataSource", t.message.toString())
            }
        })
        return tvShowResult
    }

    companion object{
        @Volatile
        private var instance: RemoteDataSource? = null
        fun getInstance(api: ApiService): RemoteDataSource =
            instance ?: synchronized(this){
                instance ?: RemoteDataSource(api).apply { instance = this }
            }
    }
}