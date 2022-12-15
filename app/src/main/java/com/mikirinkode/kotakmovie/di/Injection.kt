package com.mikirinkode.kotakmovie.di

import android.content.Context
import androidx.room.Room
import com.mikirinkode.kotakmovie.core.data.MovieRepository
import com.mikirinkode.kotakmovie.core.data.source.IMovieRepository
import com.mikirinkode.kotakmovie.core.data.source.local.LocalDataSource
import com.mikirinkode.kotakmovie.core.data.source.local.MovieDao
import com.mikirinkode.kotakmovie.core.data.source.local.MovieDatabase
import com.mikirinkode.kotakmovie.core.data.source.remote.ApiConfig
import com.mikirinkode.kotakmovie.core.data.source.remote.RemoteDataSource
import com.mikirinkode.kotakmovie.core.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext

object Injection {

    fun provideRepository(context: Context): IMovieRepository {
        val apiService = ApiConfig.getApiService()
        val database = MovieDatabase.getDatabase(context)
        val dao = database.movieDao()
        val localDataSource = LocalDataSource.getInstance(dao)
        val remoteDataSource = RemoteDataSource.getInstance(apiService)
        return MovieRepository.getInstance(remoteDataSource, localDataSource)
    }
}