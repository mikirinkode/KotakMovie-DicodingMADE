package com.mikirinkode.kotakfilm.ui.main.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mikirinkode.kotakfilm.data.MovieRepository
import com.mikirinkode.kotakfilm.data.model.MovieEntity
import com.mikirinkode.kotakfilm.utils.SortUtils
import com.mikirinkode.kotakfilm.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    private val searchQuery = MutableLiveData<String>()

    fun setSearchQuery(query: String) {
        this.searchQuery.value = query
    }

    var searchResult: LiveData<Resource<List<MovieEntity>>> =
        Transformations.switchMap(searchQuery) { query ->
            movieRepository.searchMovies(query)
        }

}