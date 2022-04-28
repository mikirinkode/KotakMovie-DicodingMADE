package com.mikirinkode.kotakmovie.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieListResponse (
    @field:SerializedName("results")
    val results: List<MovieItems>,
)

data class MovieItems(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("original_title")
    val originalTitle: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("overview")
    val overview: String,

    @field:SerializedName("release_date")
    val releaseDate: String,

    @field:SerializedName("poster_path")
    val posterPath: String,

    @field:SerializedName("backdrop_path")
    val backdropPath: String,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    )
