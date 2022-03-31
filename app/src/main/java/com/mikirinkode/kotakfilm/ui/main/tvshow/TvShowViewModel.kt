package com.mikirinkode.kotakfilm.ui.main.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mikirinkode.kotakfilm.data.MovieRepository
import com.mikirinkode.kotakfilm.data.model.TvShowEntity
import com.mikirinkode.kotakfilm.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(private val movieRepository: MovieRepository): ViewModel() {

    val tvShowId = MutableLiveData<Int>()

    fun setSelectedTvShow(tvShowId: Int){
        this.tvShowId.value = tvShowId
    }
    
    fun getPopularTvShowsList(sort: String): LiveData<Resource<List<TvShowEntity>>>{
        return movieRepository.getPopularTvShowsList(sort)

    }

    var tvShow: LiveData<Resource<TvShowEntity>> = Transformations.switchMap(tvShowId) { tvShowId ->
        movieRepository.getTvShowDetail(tvShowId)
    }
    


    fun setFavoriteTvShow(){
        val tvShowValue = tvShow.value
        if (tvShowValue != null){
            if (tvShowValue.data != null){
                val newState = !tvShowValue.data.isFavorite
                movieRepository.setFavoriteTvShow(tvShowValue.data, newState)
            }
        }
    }


}