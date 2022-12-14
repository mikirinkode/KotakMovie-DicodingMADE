package com.mikirinkode.kotakmovie.core.utils

import com.mikirinkode.kotakmovie.core.BuildConfig
import com.mikirinkode.kotakmovie.core.utils.Constants.Companion.API_KEY
import com.mikirinkode.kotakmovie.core.utils.Constants.Companion.BASE_URL
import com.mikirinkode.kotakmovie.core.utils.Constants.Companion.CERTIFICATE_PIN_1
import com.mikirinkode.kotakmovie.core.utils.Constants.Companion.CERTIFICATE_PIN_2
import com.mikirinkode.kotakmovie.core.utils.Constants.Companion.CERTIFICATE_PIN_3
import com.mikirinkode.kotakmovie.core.utils.Constants.Companion.DB_NAME
import com.mikirinkode.kotakmovie.core.utils.Constants.Companion.HOSTNAME
import com.mikirinkode.kotakmovie.core.utils.Constants.Companion.IMAGE_BASE_URL
import com.mikirinkode.kotakmovie.core.utils.Constants.Companion.PASSPHRASE

class Constants {
    companion object {
        /**
         * TODO: DON'T UPDATE BEFORE UPLOAD
         */
        const val HOSTNAME = BuildConfig.HOSTNAME
        const val BASE_URL = BuildConfig.BASE_URL
        const val IMAGE_BASE_URL = BuildConfig.IMAGE_BASE_URL

        /**
         * Get the API Key from
         * https://developers.themoviedb.org/3
         */
        const val API_KEY = BuildConfig.API_KEY
        const val DB_NAME = BuildConfig.DB_NAME
        const val PASSPHRASE = BuildConfig.PASSPHRASE
        const val CERTIFICATE_PIN_1 = BuildConfig.CERTIFICATE_PIN_1
        const val CERTIFICATE_PIN_2 = BuildConfig.CERTIFICATE_PIN_2
        const val CERTIFICATE_PIN_3 = BuildConfig.CERTIFICATE_PIN_3
    }
}