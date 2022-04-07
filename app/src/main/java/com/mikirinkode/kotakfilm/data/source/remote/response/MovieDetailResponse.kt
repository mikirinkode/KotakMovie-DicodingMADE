package com.mikirinkode.kotakfilm.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("release_date")
    val releaseDate: String,

    @SerializedName("overview")
    val overview: String?,

    @SerializedName("tagline")
    val tagline: String?,

    @field:SerializedName("genres")
    val genres: List<GenresItem>,

    @SerializedName("runtime")
    val runtime: Int?,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    )


