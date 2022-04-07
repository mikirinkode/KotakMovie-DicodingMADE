package com.mikirinkode.kotakfilm.ui.main.playlist.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mikirinkode.kotakfilm.data.MovieRepository
import com.mikirinkode.kotakfilm.data.model.CatalogueEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviePlaylistViewModel @Inject constructor(private val movieRepository: MovieRepository): ViewModel() {

    fun getMoviePlaylist(): LiveData<List<CatalogueEntity>> {
        return movieRepository.getMoviePlaylist()
    }

    fun setMoviePlaylist(movie: CatalogueEntity){
        val newState = !movie.isOnPlaylist
        movieRepository.setMoviePlaylist(movie, newState)
    }

}