package com.mikirinkode.kotakfilm.core.di

import android.content.Context
import androidx.room.Room
import com.mikirinkode.kotakfilm.core.data.source.local.MovieDao
import com.mikirinkode.kotakfilm.core.data.source.local.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

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