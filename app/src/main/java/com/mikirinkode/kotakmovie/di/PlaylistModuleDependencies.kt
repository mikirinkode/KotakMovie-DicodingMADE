package com.mikirinkode.kotakmovie.di

import com.mikirinkode.kotakmovie.core.domain.usecase.MovieUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface PlaylistModuleDependencies {

    fun movieUseCase(): MovieUseCase
}