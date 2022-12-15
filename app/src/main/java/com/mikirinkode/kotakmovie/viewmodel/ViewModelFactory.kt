package com.mikirinkode.kotakmovie.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mikirinkode.kotakmovie.core.data.source.IMovieRepository
import com.mikirinkode.kotakmovie.di.Injection
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val repository: IMovieRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
            return MovieListViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(TvShowListViewModel::class.java)) {
            return TvShowListViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(MoviePlaylistViewModel::class.java)) {
            return MoviePlaylistViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(TvShowPlaylistViewModel::class.java)) {
            return TvShowPlaylistViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}