package com.mikirinkode.kotakfilm.ui.main.playlist.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mikirinkode.kotakfilm.core.data.MovieRepository
import com.mikirinkode.kotakfilm.core.domain.model.Catalogue
import com.mikirinkode.kotakfilm.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowPlaylistViewModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel() {


    fun getTvShowPlaylist(): LiveData<List<Catalogue>> {
        return movieUseCase.getTvShowPlaylist()
    }

    fun setTvShowPlaylist(tvShow: Catalogue){
        val newState = !tvShow.isOnPlaylist
        movieUseCase.setTvShowPlaylist(tvShow, newState)
    }
}