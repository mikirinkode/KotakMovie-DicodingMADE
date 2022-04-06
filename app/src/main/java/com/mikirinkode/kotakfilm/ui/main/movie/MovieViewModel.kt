package com.mikirinkode.kotakfilm.ui.main.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mikirinkode.kotakfilm.data.MovieRepository
import com.mikirinkode.kotakfilm.data.model.MovieEntity
import com.mikirinkode.kotakfilm.data.model.TrailerVideoEntity
import com.mikirinkode.kotakfilm.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository): ViewModel() {

    private val selectedMovie = MutableLiveData<MovieEntity>()

    fun setSelectedMovie(movie: MovieEntity){
        this.selectedMovie.value = movie
    }


    var movieDetail: LiveData<Resource<MovieEntity>> = Transformations.switchMap(selectedMovie) { movie ->
        movieRepository.getMovieDetail(movie)
    }

    fun getPopularMoviesList(sort: String): LiveData<Resource<List<MovieEntity>>>{
        return movieRepository.getPopularMovies(sort)
    }

    fun getMovieTrailer(movie: MovieEntity): LiveData<Resource<List<TrailerVideoEntity>>>{
        return movieRepository.getMovieTrailer(movie)
    }

    fun setMoviePlaylist(){
        val movieValue = movieDetail.value
        if (movieValue != null){
            if (movieValue.data != null){
                val newState = !movieValue.data.isOnPlaylist
                movieRepository.setMoviePlaylist(movieValue.data, newState)
            }
        }
    }


}