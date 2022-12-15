package com.mikirinkode.kotakmovie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mikirinkode.kotakmovie.core.data.source.IMovieRepository
import com.mikirinkode.kotakmovie.core.domain.model.Catalogue
import com.mikirinkode.kotakmovie.core.vo.Resource
import com.mikirinkode.kotakmovie.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: IMovieRepository): ViewModel() {
    /**
     * UI STATE
     */
    private val _trendingState: MutableStateFlow<UiState<List<Catalogue>>> = MutableStateFlow(UiState.Loading)
    val trendingState: StateFlow<UiState<List<Catalogue>>> get() = _trendingState

    private val _upcomingState: MutableStateFlow<UiState<List<Catalogue>>> = MutableStateFlow(UiState.Loading)
    val upcomingState: StateFlow<UiState<List<Catalogue>>> get() = _upcomingState

    private val _topTvState: MutableStateFlow<UiState<List<Catalogue>>> = MutableStateFlow(UiState.Loading)
    val topTvState: StateFlow<UiState<List<Catalogue>>> get() = _topTvState

    fun getTrendingThisWeekList() {
        viewModelScope.launch {
            repository.getTrendingThisWeekList()
                .catch {
                    _trendingState.value = UiState.Error(it.message.toString())
                }
                .collect{ movieList ->
                    if (movieList.data != null){
                        _trendingState.value = UiState.Success(movieList.data)
                    }
                }
        }
    }
    fun getUpcomingMovies() {
        viewModelScope.launch {
            repository.getUpcomingMovies()
                .catch {
                    _upcomingState.value = UiState.Error(it.message.toString())
                }
                .collect{ movieList ->
                    if (movieList.data != null){
                        _upcomingState.value = UiState.Success(movieList.data)
                    }
                }
        }
    }
    fun getTopTvShowList() {
        viewModelScope.launch {
            repository.getTopTvShowList()
                .catch {
                    _topTvState.value = UiState.Error(it.message.toString())
                }
                .collect{ movieList ->
                    if (movieList.data != null){
                        _topTvState.value = UiState.Success(movieList.data)
                    }
                }
        }
    }
}
