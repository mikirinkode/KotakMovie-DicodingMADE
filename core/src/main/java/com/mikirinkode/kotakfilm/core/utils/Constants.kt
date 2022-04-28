package com.mikirinkode.kotakfilm.core.utils

import com.mikirinkode.kotakfilm.core.BuildConfig

class Constants {
    companion object{
        const val HOSTNAME = BuildConfig.HOSTNAME
        const val BASE_URL = BuildConfig.BASE_URL
        const val IMAGE_BASE_URL = BuildConfig.IMAGE_BASE_URL
        const val API_KEY = BuildConfig.API_KEY
        const val DB_NAME = BuildConfig.DB_NAME
        const val PASSPHRASE = BuildConfig.PASSPHRASE
        const val CERTIFICATE_PIN_1 = BuildConfig.CERTIFICATE_PIN_1
        const val CERTIFICATE_PIN_2 = BuildConfig.CERTIFICATE_PIN_2
        const val CERTIFICATE_PIN_3 = BuildConfig.CERTIFICATE_PIN_3
    }
}