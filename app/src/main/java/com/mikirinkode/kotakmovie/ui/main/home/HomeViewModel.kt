package com.mikirinkode.kotakmovie.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mikirinkode.kotakmovie.core.domain.model.Catalogue
import com.mikirinkode.kotakmovie.core.domain.usecase.MovieUseCase
import com.mikirinkode.kotakmovie.core.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val movieUseCase: MovieUseCase): ViewModel() {
    fun getTrendingThisWeekList(): LiveData<Resource<List<Catalogue>>> {
        return movieUseCase.getTrendingThisWeekList().asLiveData()
    }

    fun getUpcomingMovies(): LiveData<Resource<List<Catalogue>>> {
        return movieUseCase.getUpcomingMovies().asLiveData()
    }

    fun getTopTvShowList(): LiveData<Resource<List<Catalogue>>> {
        return movieUseCase.getTopTvShowList().asLiveData()
    }

}
