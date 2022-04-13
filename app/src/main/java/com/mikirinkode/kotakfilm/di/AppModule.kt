package com.mikirinkode.kotakfilm.di

import com.mikirinkode.kotakfilm.core.domain.usecase.MovieInteractor
import com.mikirinkode.kotakfilm.core.domain.usecase.MovieUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase
}