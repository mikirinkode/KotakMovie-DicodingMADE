package com.mikirinkode.kotakfilm.ui.main.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mikirinkode.kotakfilm.core.data.MovieRepository
import com.mikirinkode.kotakfilm.core.data.entity.TrailerVideoEntity
import com.mikirinkode.kotakfilm.core.domain.model.Catalogue
import com.mikirinkode.kotakfilm.core.domain.model.TrailerVideo
import com.mikirinkode.kotakfilm.core.domain.usecase.MovieUseCase
import com.mikirinkode.kotakfilm.core.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel() {

    private val selectedTvShow = MutableLiveData<Catalogue>()

    fun setSelectedTvShow(tvShow: Catalogue){
        this.selectedTvShow.value = tvShow
    }
    
    fun getPopularTvShowsList(sort: String): LiveData<Resource<List<Catalogue>>>{
        return movieUseCase.getPopularTvShows(sort)

    }

    var tvShowDetail: LiveData<Resource<Catalogue>> = Transformations.switchMap(selectedTvShow) { tvShow ->
        movieUseCase.getTvShowDetail(tvShow)
    }

    fun getTvTrailer(tvShow: Catalogue): LiveData<Resource<TrailerVideo>>{
        return movieUseCase.getTvTrailer(tvShow)
    }

    fun setTvShowPlaylist(){
        val tvShowValue = tvShowDetail.value
        if (tvShowValue != null){
            if (tvShowValue.data != null){
                val newState = !tvShowValue.data.isOnPlaylist
                movieUseCase.setTvShowPlaylist(tvShowValue.data, newState)
            }
        }
    }


}