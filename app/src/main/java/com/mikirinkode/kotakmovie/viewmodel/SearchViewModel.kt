package com.mikirinkode.kotakmovie.viewmodel

import androidx.lifecycle.*
import com.mikirinkode.kotakmovie.core.data.source.IMovieRepository
import com.mikirinkode.kotakmovie.core.domain.model.Catalogue
import com.mikirinkode.kotakmovie.core.vo.Resource
import com.mikirinkode.kotakmovie.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel(private val repository: IMovieRepository) : ViewModel() {

    /**
     * UI STATE
     */
    private val _uiState: MutableStateFlow<UiState<List<Catalogue>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Catalogue>>>
        get() = _uiState

//    private val searchQuery = MutableLiveData<String>()
//
//    fun setSearchQuery(query: String) {
//        this.searchQuery.value = query
//    }
//
//    var searchResult: LiveData<Resource<List<Catalogue>>> =
//        Transformations.switchMap(searchQuery) { query ->
//            repository.searchMovies(query).asLiveData()
//        }

    fun searchMovies(query: String){
        viewModelScope.launch {
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
