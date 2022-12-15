package com.mikirinkode.kotakmovie.core.utils

import com.mikirinkode.kotakmovie.BuildConfig

class Constants {
    companion object {
        const val BASE_URL = BuildConfig.BASE_URL
        const val IMAGE_BASE_URL = BuildConfig.IMAGE_BASE_URL

        /**
         * Get the API Key from
         * https://developers.themoviedb.org/3
         */
        const val API_KEY = BuildConfig.API_KEY
        const val DB_NAME = BuildConfig.DB_NAME
    }
}