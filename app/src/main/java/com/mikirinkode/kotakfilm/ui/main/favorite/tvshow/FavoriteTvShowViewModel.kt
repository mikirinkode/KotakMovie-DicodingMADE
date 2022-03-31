package com.mikirinkode.kotakfilm.ui.main.favorite.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mikirinkode.kotakfilm.data.MovieRepository
import com.mikirinkode.kotakfilm.data.model.TvShowEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteTvShowViewModel @Inject constructor(private val movieRepository: MovieRepository): ViewModel() {


    fun getFavoriteTvShowList(): LiveData<List<TvShowEntity>> {
        return movieRepository.getFavoriteTvShows()
    }

    fun setFavorite(tvShow: TvShowEntity){
        val newState = !tvShow.isFavorite
        movieRepository.setFavoriteTvShow(tvShow, newState)
    }
}