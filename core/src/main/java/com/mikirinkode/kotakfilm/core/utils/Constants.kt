package com.mikirinkode.kotakfilm.core.utils

import com.mikirinkode.kotakfilm.core.BuildConfig

class Constants {
    companion object{
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val API_KEY = BuildConfig.TMDB_API_KEY
    }
}