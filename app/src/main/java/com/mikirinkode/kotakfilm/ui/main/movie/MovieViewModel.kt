package com.mikirinkode.kotakfilm.ui.main.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mikirinkode.kotakfilm.data.MovieRepository
import com.mikirinkode.kotakfilm.data.model.MovieEntity
import com.mikirinkode.kotakfilm.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository): ViewModel() {

    private val movieId = MutableLiveData<Int>()

    fun setSelectedMovie(movieId: Int){
        this.movieId.value = movieId
    }


    var movie: LiveData<Resource<MovieEntity>> = Transformations.switchMap(movieId) { mMovieId ->
        movieRepository.getMovieDetail(mMovieId)
    }

    fun getPopularMoviesList(sort: String): LiveData<Resource<List<MovieEntity>>>{
        return movieRepository.getPopularMoviesList(sort)
    }

    fun setFavoriteMovie(){
        val movieValue = movie.value
        if (movieValue != null){
            if (movieValue.data != null){
                val newState = !movieValue.data.isFavorite
                movieRepository.setFavoriteMovie(movieValue.data, newState)
            }
        }
    }


}