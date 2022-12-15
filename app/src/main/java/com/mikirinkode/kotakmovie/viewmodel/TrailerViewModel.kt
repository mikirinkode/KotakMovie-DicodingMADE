package com.mikirinkode.kotakmovie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikirinkode.kotakmovie.core.data.source.IMovieRepository
import com.mikirinkode.kotakmovie.core.domain.model.Catalogue
import com.mikirinkode.kotakmovie.core.domain.model.TrailerVideo
import com.mikirinkode.kotakmovie.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TrailerViewModel(private val repository: IMovieRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<TrailerVideo>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<TrailerVideo>>>
        get() = _uiState

    fun getMovieTrailer(movieId: Int) {
        viewModelScope.launch {
            repository.getMovieTrailer(movieId)
                .catch {
                        _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    if (it.data != null){
                        _uiState.value = UiState.Success(it.data)
                    }
                }
        }
    }
    fun getTvShowTrailer(tvShowId: Int) {
        viewModelScope.launch {
            repository.getTvTrailer(tvShowId)
                .catch {
                        _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {
                    if (it.data != null){
                        _uiState.value = UiState.Success(it.data)
                    }
                }
        }
    }
}