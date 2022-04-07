package com.mikirinkode.kotakfilm.ui.main.playlist.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mikirinkode.kotakfilm.core.data.MovieRepository
import com.mikirinkode.kotakfilm.core.domain.model.Catalogue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviePlaylistViewModel @Inject constructor(private val movieRepository: MovieRepository): ViewModel() {

    fun getMoviePlaylist(): LiveData<List<Catalogue>> {
        return movieRepository.getMoviePlaylist()
    }

    fun setMoviePlaylist(movie: Catalogue){
        val newState = !movie.isOnPlaylist
        movieRepository.setMoviePlaylist(movie, newState)
    }

}