package com.mikirinkode.kotakfilm.core.di

import com.mikirinkode.kotakfilm.core.data.MovieRepository
import com.mikirinkode.kotakfilm.core.data.source.local.LocalDataSource
import com.mikirinkode.kotakfilm.core.data.source.local.MovieDao
import com.mikirinkode.kotakfilm.core.data.source.remote.ApiService
import com.mikirinkode.kotakfilm.core.data.source.remote.RemoteDataSource
import com.mikirinkode.kotakfilm.core.domain.repository.IMovieRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

//    @Singleton
//    @Provides
//    fun provideRemoteDataSource(api: ApiService): RemoteDataSource {
//        return RemoteDataSource.getInstance(api)
//    }
//
//    @Singleton
//    @Provides
//    fun provideLocalDataSource(movieDao: MovieDao): LocalDataSource {
//        return LocalDataSource.getInstance(movieDao)
//    }

    @Binds
    abstract fun provideRepository(movieRepository: MovieRepository): IMovieRepository
}