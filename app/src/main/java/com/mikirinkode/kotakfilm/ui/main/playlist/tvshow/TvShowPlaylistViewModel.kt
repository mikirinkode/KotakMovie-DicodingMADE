package com.mikirinkode.kotakfilm.ui.main.playlist.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mikirinkode.kotakfilm.data.MovieRepository
import com.mikirinkode.kotakfilm.data.model.CatalogueEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowPlaylistViewModel @Inject constructor(private val movieRepository: MovieRepository): ViewModel() {


    fun getTvShowPlaylist(): LiveData<List<CatalogueEntity>> {
        return movieRepository.getTvShowPlaylist()
    }

    fun setTvShowPlaylist(tvShow: CatalogueEntity){
        val newState = !tvShow.isOnPlaylist
        movieRepository.setTvShowPlaylist(tvShow, newState)
    }
}