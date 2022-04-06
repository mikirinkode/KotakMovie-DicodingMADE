package com.mikirinkode.kotakfilm.data.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TrailerVideoEntities")
data class TrailerVideoEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "trailerId")
    val trailerId: String,

    @ColumnInfo(name = "movieId")
    val movieId: Int,

    @ColumnInfo(name = "key")
    val key: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "site")
    val site: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "official")
    val official: Boolean
)
