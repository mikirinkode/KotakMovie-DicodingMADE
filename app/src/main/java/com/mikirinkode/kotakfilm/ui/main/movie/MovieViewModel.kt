package com.mikirinkode.kotakfilm.ui.main.movie

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
class MovieViewModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel() {

    private val selectedMovie = MutableLiveData<Catalogue>()

    fun setSelectedMovie(movie: Catalogue){
        this.selectedMovie.value = movie
    }


    var movieDetail: LiveData<Resource<Catalogue>> = Transformations.switchMap(selectedMovie) { movie ->
        movieUseCase.getMovieDetail(movie)
    }

    fun getPopularMoviesList(sort: String): LiveData<Resource<List<Catalogue>>>{
        return movieUseCase.getPopularMovies(sort)
    }

    fun getMovieTrailer(movie: Catalogue): LiveData<Resource<TrailerVideo>>{
        return movieUseCase.getMovieTrailer(movie)
    }

    fun setMoviePlaylist(){
        val movieValue = movieDetail.value
        if (movieValue != null){
            if (movieValue.data != null){
                val newState = !movieValue.data.isOnPlaylist
                movieUseCase.setMoviePlaylist(movieValue.data, newState)
            }
        }
    }


}