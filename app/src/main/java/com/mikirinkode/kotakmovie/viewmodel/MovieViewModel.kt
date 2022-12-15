package com.mikirinkode.kotakmovie.viewmodel

import androidx.lifecycle.*
import com.mikirinkode.kotakmovie.core.data.source.IMovieRepository
import com.mikirinkode.kotakmovie.core.domain.model.Catalogue
import com.mikirinkode.kotakmovie.core.domain.model.TrailerVideo
import com.mikirinkode.kotakmovie.core.vo.Resource
import com.mikirinkode.kotakmovie.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieViewModel(private val repository: IMovieRepository) : ViewModel() {

    /**
     * UI STATE
     */
    private val _uiState: MutableStateFlow<UiState<List<Catalogue>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Catalogue>>>
        get() = _uiState

    // changed


    private val selectedMovie = MutableLiveData<Catalogue>()

    fun setSelectedMovie(movie: Catalogue) {
        this.selectedMovie.value = movie
    }


    var movieDetail: LiveData<Resource<Catalogue>> =
        Transformations.switchMap(selectedMovie) { movie ->
            repository.getMovieDetail(movie).asLiveData()
        }


    fun getMovieTrailer(movie: Catalogue): LiveData<Resource<List<TrailerVideo>>> {
        return repository.getMovieTrailer(movie).asLiveData()
    }

    fun insertMovieToPlaylist(item: Catalogue, newState: Boolean) {
        viewModelScope.launch {
            repository.insertPlaylistItem(item, newState)
        }
    }

    fun removeMovieFromPlaylist(item: Catalogue){
        viewModelScope.launch { repository.removePlaylistItem(item) }
    }
}