package com.mikirinkode.kotakfilm.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {
    const val LATEST = "Latest"
    const val OLDEST = "Oldest"
    const val BEST = "Best"
    const val WORST = "Worst"
    const val RANDOM = "Random"
    const val MOVIE_TABLE = "MovieEntities"
    const val TV_TABLE = "TvShowEntities"

    fun getSortedQuery(filter: String, tableName: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM $tableName ")
        when (filter) {
            LATEST -> simpleQuery.append("ORDER BY releaseDate DESC")
            OLDEST -> simpleQuery.append("ORDER BY releaseDate ASC")
            BEST -> simpleQuery.append("ORDER BY voteAverage DESC")
            WORST -> simpleQuery.append("ORDER BY voteAverage ASC")
            RANDOM -> simpleQuery.append("ORDER BY RANDOM()")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }

}