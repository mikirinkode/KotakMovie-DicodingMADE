package com.mikirinkode.kotakmovie.ui.main.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mikirinkode.kotakmovie.core.domain.model.Catalogue
import com.mikirinkode.kotakmovie.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel() {

    fun getMoviePlaylist(): LiveData<List<Catalogue>> {
        return movieUseCase.getMoviePlaylist().asLiveData()
    }

    fun getTvShowPlaylist(): LiveData<List<Catalogue>> {
        return movieUseCase.getTvShowPlaylist().asLiveData()
    }
}