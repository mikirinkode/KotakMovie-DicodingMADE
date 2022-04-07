package com.mikirinkode.kotakfilm.ui.main.playlist.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mikirinkode.kotakfilm.core.data.MovieRepository
import com.mikirinkode.kotakfilm.core.domain.model.Catalogue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowPlaylistViewModel @Inject constructor(private val movieRepository: MovieRepository): ViewModel() {


    fun getTvShowPlaylist(): LiveData<List<Catalogue>> {
        return movieRepository.getTvShowPlaylist()
    }

    fun setTvShowPlaylist(tvShow: Catalogue){
        val newState = !tvShow.isOnPlaylist
        movieRepository.setTvShowPlaylist(tvShow, newState)
    }
}