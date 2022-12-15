package com.mikirinkode.kotakmovie.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikirinkode.kotakmovie.core.data.source.IMovieRepository
import com.mikirinkode.kotakmovie.core.domain.model.Catalogue
import com.mikirinkode.kotakmovie.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailMovieViewModel(private val repository: IMovieRepository): ViewModel() {
    /**
     * UI STATE
     */
    private val _uiState: MutableStateFlow<UiState<Catalogue>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Catalogue>>
        get() = _uiState

    fun getMovieDetail(movieId: Int){
        viewModelScope.launch {
            repository.getMovieDetail(movieId)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect{ movie ->
                    if (movie.data != null){
                        _uiState.value = UiState.Success(movie.data)
                    }
                }
        }
    }

    fun insertMovieToPlaylist(item: Catalogue, newState: Boolean) {
        viewModelScope.launch {
            repository.insertPlaylistItem(item, newState)

        }
    }

    fun removeMovieFromPlaylist(item: Catalogue){
        viewModelScope.launch { repository.removePlaylistItem(item) }
    }

    fun insertTvShowToPlaylist(item: Catalogue, newState: Boolean) {
        viewModelScope.launch {
            repository.insertPlaylistItem(item, newState)
        }
    }

    fun removeTvShowFromPlaylist(item: Catalogue) {
        viewModelScope.launch { repository.removePlaylistItem(item) }
    }

    fun getTvShowDetail(tvShowId: Int){
        viewModelScope.launch {
            repository.getTvShowDetail(tvShowId)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect{ tvShow ->
                    if (tvShow.data != null){
                        _uiState.value = UiState.Success(tvShow.data)
                    }
                }
        }
    }
}