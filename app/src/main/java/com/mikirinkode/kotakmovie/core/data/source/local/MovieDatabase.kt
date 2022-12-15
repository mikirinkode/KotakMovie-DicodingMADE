package com.mikirinkode.kotakmovie.core.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mikirinkode.kotakmovie.core.data.entity.CatalogueEntity
import com.mikirinkode.kotakmovie.core.utils.Constants

@Database(
    entities = [CatalogueEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): MovieDatabase {
            if (INSTANCE == null) {
                synchronized(MovieDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        MovieDatabase::class.java, Constants.DB_NAME)
                        .build()
                }
            }
            return INSTANCE as MovieDatabase
        }
    }
}