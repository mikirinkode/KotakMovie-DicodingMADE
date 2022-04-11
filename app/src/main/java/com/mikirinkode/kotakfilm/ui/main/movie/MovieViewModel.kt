package com.mikirinkode.kotakfilm.ui.main.movie

import androidx.lifecycle.*
import com.mikirinkode.kotakfilm.core.data.MovieRepository
import com.mikirinkode.kotakfilm.core.data.entity.TrailerVideoEntity
import com.mikirinkode.kotakfilm.core.domain.model.Catalogue
import com.mikirinkode.kotakfilm.core.domain.model.TrailerVideo
import com.mikirinkode.kotakfilm.core.domain.usecase.MovieUseCase
import com.mikirinkode.kotakfilm.core.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieUseCase: MovieUseCase) : ViewModel() {

    private val selectedMovie = MutableLiveData<Catalogue>()

    fun setSelectedMovie(movie: Catalogue) {
        this.selectedMovie.value = movie
    }


    var movieDetail: LiveData<Resource<Catalogue>> =
        Transformations.switchMap(selectedMovie) { movie ->
            movieUseCase.getMovieDetail(movie).asLiveData()
        }

    fun getPopularMoviesList(sort: String): LiveData<Resource<List<Catalogue>>> {
        return movieUseCase.getPopularMovies(sort).asLiveData()
    }

    fun getMovieTrailer(movie: Catalogue): LiveData<Resource<TrailerVideo>> {
        return movieUseCase.getMovieTrailer(movie).asLiveData()
    }

    fun insertMovieToPlaylist() {
        val movieValue = movieDetail.value
        if (movieValue != null) {
            if (movieValue.data != null) {
                val newState = !movieValue.data.isOnPlaylist
                viewModelScope.launch {
                    movieUseCase.insertPlaylistItem(movieValue.data, newState)
                }
            }
        }
    }

    fun removeMovieFromPlaylist(item: Catalogue){
        viewModelScope.launch { movieUseCase.removePlaylistItem(item) }
    }
}