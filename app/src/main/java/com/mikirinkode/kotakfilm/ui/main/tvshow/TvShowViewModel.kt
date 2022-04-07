package com.mikirinkode.kotakfilm.ui.main.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mikirinkode.kotakfilm.data.MovieRepository
import com.mikirinkode.kotakfilm.data.model.TrailerVideoEntity
import com.mikirinkode.kotakfilm.data.model.CatalogueEntity
import com.mikirinkode.kotakfilm.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(private val movieRepository: MovieRepository): ViewModel() {

    private val selectedTvShow = MutableLiveData<CatalogueEntity>()

    fun setSelectedTvShow(tvShow: CatalogueEntity){
        this.selectedTvShow.value = tvShow
    }
    
    fun getPopularTvShowsList(sort: String): LiveData<Resource<List<CatalogueEntity>>>{
        return movieRepository.getPopularTvShows(sort)

    }

    var tvShowDetail: LiveData<Resource<CatalogueEntity>> = Transformations.switchMap(selectedTvShow) { tvShow ->
        movieRepository.getTvShowDetail(tvShow)
    }

    fun getTvTrailer(tvShow: CatalogueEntity): LiveData<Resource<List<TrailerVideoEntity>>>{
        return movieRepository.getTvTrailer(tvShow)
    }

    fun setTvShowPlaylist(){
        val tvShowValue = tvShowDetail.value
        if (tvShowValue != null){
            if (tvShowValue.data != null){
                val newState = !tvShowValue.data.isOnPlaylist
                movieRepository.setTvShowPlaylist(tvShowValue.data, newState)
            }
        }
    }


}