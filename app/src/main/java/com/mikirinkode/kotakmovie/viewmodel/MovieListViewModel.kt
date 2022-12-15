package com.mikirinkode.kotakmovie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikirinkode.kotakmovie.core.data.source.IMovieRepository
import com.mikirinkode.kotakmovie.core.domain.model.Catalogue
import com.mikirinkode.kotakmovie.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MovieListViewModel(private val repository: IMovieRepository) : ViewModel() {

    /**
     * UI STATE
     */
    private val _uiState: MutableStateFlow<UiState<List<Catalogue>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Catalogue>>>
        get() = _uiState

    fun getPopularMoviesList(sort: String, shouldFetchAgain: Boolean){
        viewModelScope.launch {
            repository.getPopularMovies(sort, shouldFetchAgain)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect{ movieList ->
                    if (movieList.data != null){
                        _uiState.value = UiState.Success(movieList.data)
                    }
                }
        }
    }
}
