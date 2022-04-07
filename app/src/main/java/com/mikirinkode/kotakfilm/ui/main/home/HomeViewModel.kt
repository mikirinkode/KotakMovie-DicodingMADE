package com.mikirinkode.kotakfilm.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mikirinkode.kotakfilm.data.MovieRepository
import com.mikirinkode.kotakfilm.data.model.CatalogueEntity
import com.mikirinkode.kotakfilm.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieRepository: MovieRepository): ViewModel() {
    fun getTrendingMovies(): LiveData<Resource<List<CatalogueEntity>>> {
        return movieRepository.getTrendingMovies()
    }

    fun getUpcomingMovies(): LiveData<Resource<List<CatalogueEntity>>> {
        return movieRepository.getUpcomingMovies()
    }

    fun getTopTvShowList(): LiveData<Resource<List<CatalogueEntity>>> {
        return movieRepository.getTopTvShowList()
    }

}
