package com.mikirinkode.kotakmovie.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikirinkode.kotakmovie.core.data.source.IMovieRepository
import com.mikirinkode.kotakmovie.core.domain.model.Catalogue
import com.mikirinkode.kotakmovie.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: IMovieRepository) : ViewModel() {

    /**
     * UI STATE
     */
    private val _uiState: MutableStateFlow<UiState<List<Catalogue>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Catalogue>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun clearQuery(){
        _query.value = ""
    }

    fun searchMovies(query: String){
        viewModelScope.launch {
            _query.value = query
            repository.searchMovies(query)
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
