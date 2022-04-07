package com.mikirinkode.kotakfilm.core.domain.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class Catalogue(
    val id: Int,
    val title: String,
    val releaseDate: String?,
    val overview: String?,
    val tagline: String?,
    val genres: String?,
    val runtime: Int?,
    val voteAverage: Double,
    val posterPath: String?,
    val backdropPath: String?,
    var isOnPlaylist: Boolean = false,
    var isTvShow: Boolean = false

): Parcelable

