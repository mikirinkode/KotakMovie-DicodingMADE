package com.mikirinkode.kotakfilm.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mikirinkode.kotakfilm.data.model.MovieEntity
import com.mikirinkode.kotakfilm.data.model.TrailerVideoEntity
import com.mikirinkode.kotakfilm.data.model.TvShowEntity


@Database(
    entities = [MovieEntity::class, TvShowEntity::class, TrailerVideoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao
}