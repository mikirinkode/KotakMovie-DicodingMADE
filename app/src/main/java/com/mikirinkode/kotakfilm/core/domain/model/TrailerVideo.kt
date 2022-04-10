package com.mikirinkode.kotakfilm.core.domain.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class TrailerVideo(
    val trailerId: String,
    val key: String,
    val name: String,
    val site: String,
    val type: String,
    val official: Boolean
)
