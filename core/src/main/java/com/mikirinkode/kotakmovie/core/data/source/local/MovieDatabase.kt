package com.mikirinkode.kotakmovie.core.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mikirinkode.kotakmovie.core.data.entity.CatalogueEntity

@Database(
    entities = [CatalogueEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao
}