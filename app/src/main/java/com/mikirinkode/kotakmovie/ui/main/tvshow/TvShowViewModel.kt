package com.mikirinkode.kotakmovie.ui.main.tvshow

import androidx.lifecycle.*
import com.mikirinkode.kotakmovie.core.domain.model.Catalogue
import com.mikirinkode.kotakmovie.core.domain.model.TrailerVideo
import com.mikirinkode.kotakmovie.core.domain.usecase.MovieUseCase
import com.mikirinkode.kotakmovie.core.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel() {

    private val selectedTvShow = MutableLiveData<Catalogue>()

    fun setSelectedTvShow(tvShow: Catalogue){
        this.selectedTvShow.value = tvShow
    }
    
    fun getPopularTvShowsList(sort: String, shouldFetchAgain: Boolean): LiveData<Resource<List<Catalogue>>>{
        return movieUseCase.getPopularTvShows(sort, shouldFetchAgain).asLiveData()

    }

    var tvShowDetail: LiveData<Resource<Catalogue>> = Transformations.switchMap(selectedTvShow) { tvShow ->
        movieUseCase.getTvShowDetail(tvShow).asLiveData()
    }

    fun getTvTrailer(tvShow: Catalogue): LiveData<Resource<List<TrailerVideo>>>{
        return movieUseCase.getTvTrailer(tvShow).asLiveData()
    }

    fun insertTvShowToPlaylist(item: Catalogue, newState: Boolean){
        viewModelScope.launch {
            movieUseCase.insertPlaylistItem(item, newState)
        }
    }

    fun removeTvShowFromPlaylist(item: Catalogue){
        viewModelScope.launch { movieUseCase.removePlaylistItem(item) }
    }
}