package com.mikirinkode.kotakfilm.ui.main.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mikirinkode.kotakfilm.core.data.MovieRepository
import com.mikirinkode.kotakfilm.core.data.entity.TrailerVideoEntity
import com.mikirinkode.kotakfilm.core.domain.model.Catalogue
import com.mikirinkode.kotakfilm.core.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository): ViewModel() {

    private val selectedMovie = MutableLiveData<Catalogue>()

    fun setSelectedMovie(movie: Catalogue){
        this.selectedMovie.value = movie
    }


    var movieDetail: LiveData<Resource<Catalogue>> = Transformations.switchMap(selectedMovie) { movie ->
        movieRepository.getMovieDetail(movie)
    }

    fun getPopularMoviesList(sort: String): LiveData<Resource<List<Catalogue>>>{
        return movieRepository.getPopularMovies(sort)
    }

    fun getMovieTrailer(movie: Catalogue): LiveData<Resource<List<TrailerVideoEntity>>>{
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