package com.mikirinkode.kotakfilm.ui.search

import androidx.lifecycle.*
import com.mikirinkode.kotakfilm.core.domain.model.Catalogue
import com.mikirinkode.kotakfilm.core.domain.usecase.MovieUseCase
import com.mikirinkode.kotakfilm.core.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val movieUseCase: MovieUseCase) :
    ViewModel() {

    private val searchQuery = MutableLiveData<String>()

    fun setSearchQuery(query: String) {
        this.searchQuery.value = query
    }

    var searchResult: LiveData<Resource<List<Catalogue>>> =
        Transformations.switchMap(searchQuery) { query ->
            movieUseCase.searchMovies(query).asLiveData()
        }

}
