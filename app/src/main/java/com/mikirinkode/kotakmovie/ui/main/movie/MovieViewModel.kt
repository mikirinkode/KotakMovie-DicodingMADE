package com.mikirinkode.kotakmovie.ui.main.movie

import androidx.lifecycle.*
import com.mikirinkode.kotakmovie.core.domain.model.Catalogue
import com.mikirinkode.kotakmovie.core.domain.model.TrailerVideo
import com.mikirinkode.kotakmovie.core.domain.usecase.MovieUseCase
import com.mikirinkode.kotakmovie.core.vo.Resource
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

    fun getPopularMoviesList(sort: String, shouldFetchAgain: Boolean): LiveData<Resource<List<Catalogue>>> {
        return movieUseCase.getPopularMovies(sort, shouldFetchAgain).asLiveData()
    }

    fun getMovieTrailer(movie: Catalogue): LiveData<Resource<List<TrailerVideo>>> {
        return movieUseCase.getMovieTrailer(movie).asLiveData()
    }

    fun insertMovieToPlaylist(item: Catalogue, newState: Boolean) {
        viewModelScope.launch {
            movieUseCase.insertPlaylistItem(item, newState)
        }
    }

    fun removeMovieFromPlaylist(item: Catalogue){
        viewModelScope.launch { movieUseCase.removePlaylistItem(item) }
    }
}