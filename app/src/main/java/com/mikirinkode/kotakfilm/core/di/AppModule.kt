package com.mikirinkode.kotakfilm.core.di

import android.content.Context
import androidx.room.Room
import com.mikirinkode.kotakfilm.core.data.MovieRepository
import com.mikirinkode.kotakfilm.core.data.source.local.LocalDataSource
import com.mikirinkode.kotakfilm.core.data.source.local.MovieDao
import com.mikirinkode.kotakfilm.core.data.source.local.MovieDatabase
import com.mikirinkode.kotakfilm.core.data.source.remote.ApiService
import com.mikirinkode.kotakfilm.core.data.source.remote.RemoteDataSource
import com.mikirinkode.kotakfilm.core.domain.usecase.MovieInteractor
import com.mikirinkode.kotakfilm.core.domain.usecase.MovieUseCase
import com.mikirinkode.kotakfilm.core.utils.AppExecutors
import com.mikirinkode.kotakfilm.core.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(api: ApiService): RemoteDataSource {
        return RemoteDataSource.getInstance(api)
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(movieDao: MovieDao): LocalDataSource {
        return LocalDataSource.getInstance(movieDao)
    }

    @Singleton
    @Provides
    fun provideRepository(localDataSource: LocalDataSource, remoteDataSource: RemoteDataSource): MovieRepository {
        return MovieRepository.getInstance(remoteDataSource, localDataSource)
    }

    @Singleton
    @Provides
    fun provideMovieUseCase(movieRepository: MovieRepository): MovieUseCase{
        return MovieInteractor(movieRepository)
    }

    @Singleton
    @Provides
    fun provideMovieDatabase(@ApplicationContext app: Context): MovieDatabase {
       return Room.databaseBuilder(app, MovieDatabase::class.java, "kotakfilm_db").build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao {
        return database.movieDao()
    }
}