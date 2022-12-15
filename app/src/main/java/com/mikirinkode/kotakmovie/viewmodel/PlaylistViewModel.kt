package com.mikirinkode.kotakmovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mikirinkode.kotakmovie.core.data.source.IMovieRepository
import com.mikirinkode.kotakmovie.core.domain.model.Catalogue
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class PlaylistViewModel(private val repository: IMovieRepository): ViewModel() {

    fun getMoviePlaylist(): LiveData<List<Catalogue>> {
        return repository.getMoviePlaylist().asLiveData()
    }

    fun getTvShowPlaylist(): LiveData<List<Catalogue>> {
        return repository.getTvShowPlaylist().asLiveData()
    }
}