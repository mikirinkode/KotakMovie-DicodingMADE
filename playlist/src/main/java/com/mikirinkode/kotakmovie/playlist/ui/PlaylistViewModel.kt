package com.mikirinkode.kotakmovie.playlist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mikirinkode.kotakmovie.core.domain.model.Catalogue
import com.mikirinkode.kotakmovie.core.domain.usecase.MovieUseCase

class PlaylistViewModel(private val movieUseCase: MovieUseCase): ViewModel() {

    fun getMoviePlaylist(): LiveData<List<Catalogue>> {
        return movieUseCase.getMoviePlaylist().asLiveData()
    }

    fun getTvShowPlaylist(): LiveData<List<Catalogue>> {
        return movieUseCase.getTvShowPlaylist().asLiveData()
    }
}