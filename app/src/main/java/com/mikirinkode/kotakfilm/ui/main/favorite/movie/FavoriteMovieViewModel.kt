package com.mikirinkode.kotakfilm.ui.main.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.mikirinkode.kotakfilm.data.MovieRepository
import com.mikirinkode.kotakfilm.data.model.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteMovieViewModel @Inject constructor(private val movieRepository: MovieRepository): ViewModel() {

    fun getFavoriteMovieList(): LiveData<List<MovieEntity>> {
        return movieRepository.getFavoriteMovies()
    }

    fun setFavorite(movie: MovieEntity){
        val newState = !movie.isFavorite
        movieRepository.setFavoriteMovie(movie, newState)
    }

}