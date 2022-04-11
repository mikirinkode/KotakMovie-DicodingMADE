package com.mikirinkode.kotakfilm.ui.main.playlist.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mikirinkode.kotakfilm.core.data.MovieRepository
import com.mikirinkode.kotakfilm.core.domain.model.Catalogue
import com.mikirinkode.kotakfilm.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviePlaylistViewModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel() {

    fun getMoviePlaylist(): LiveData<List<Catalogue>> {
        return movieUseCase.getMoviePlaylist().asLiveData()
    }
}