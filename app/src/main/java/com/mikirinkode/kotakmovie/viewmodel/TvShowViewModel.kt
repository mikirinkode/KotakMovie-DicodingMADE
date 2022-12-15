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

class TvShowViewModel(private val repository: IMovieRepository) : ViewModel() {

    /**
     * UI STATE
     */
    private val _uiState: MutableStateFlow<UiState<List<Catalogue>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Catalogue>>>
        get() = _uiState

    fun getPopularTvShowsList(sort: String, shouldFetchAgain: Boolean) {
        viewModelScope.launch {
            repository.getPopularTvShows(sort, shouldFetchAgain)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect{ tvList ->
                    if (tvList.data.isNullOrEmpty()){
                        _uiState.value = UiState.Success(listOf())
                    } else {
                        _uiState.value = UiState.Success(tvList.data)
                    }
                }
        }
    }

    private val selectedTvShow = MutableLiveData<Catalogue>()

    fun setSelectedTvShow(tvShow: Catalogue) {
        this.selectedTvShow.value = tvShow
    }


    var tvShowDetail: LiveData<Resource<Catalogue>> =
        Transformations.switchMap(selectedTvShow) { tvShow ->
            repository.getTvShowDetail(tvShow).asLiveData()
        }

    fun getTvTrailer(tvShow: Catalogue): LiveData<Resource<List<TrailerVideo>>> {
        return repository.getTvTrailer(tvShow).asLiveData()
    }

    fun insertTvShowToPlaylist(item: Catalogue, newState: Boolean) {
        viewModelScope.launch {
            repository.insertPlaylistItem(item, newState)
        }
    }

    fun removeTvShowFromPlaylist(item: Catalogue) {
        viewModelScope.launch { repository.removePlaylistItem(item) }
    }
}