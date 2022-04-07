package com.mikirinkode.kotakfilm.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {
    const val LATEST = "Latest"
    const val OLDEST = "Oldest"
    const val BEST = "Best"
    const val WORST = "Worst"
    const val RANDOM = "Random"

    fun getSortedQuery(filter: String, isTvShow: Boolean): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM CatalogueEntities WHERE isTvShow = $isTvShow ")
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