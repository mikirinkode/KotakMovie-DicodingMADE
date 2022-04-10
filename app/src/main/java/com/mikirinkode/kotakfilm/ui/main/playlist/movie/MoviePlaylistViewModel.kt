package com.mikirinkode.kotakfilm.ui.main.playlist.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mikirinkode.kotakfilm.core.data.MovieRepository
import com.mikirinkode.kotakfilm.core.domain.model.Catalogue
import com.mikirinkode.kotakfilm.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviePlaylistViewModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel() {

    fun getMoviePlaylist(): LiveData<List<Catalogue>> {
        return movieUseCase.getMoviePlaylist()
    }

    fun setMoviePlaylist(movie: Catalogue){
        val newState = !movie.isOnPlaylist
        movieUseCase.setMoviePlaylist(movie, newState)
    }

}