package com.mikirinkode.kotakmovie.playlist.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mikirinkode.kotakmovie.core.domain.usecase.MovieUseCase
import com.mikirinkode.kotakmovie.playlist.ui.PlaylistViewModel
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val movieUseCase: MovieUseCase): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(PlaylistViewModel::class.java) -> {
                PlaylistViewModel(movieUseCase) as T
            }
            else -> throw  Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}