package com.mikirinkode.kotakmovie.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowListResponse (
    @field:SerializedName("results")
    val results: List<TvItems>,
)

data class TvItems(

    @field:SerializedName("id")
    val id: Int,

    @SerializedName("original_name")
    val originalName: String,

    @SerializedName("name")
    val name: String,

    @field:SerializedName("overview")
    val overview: String,

    @SerializedName("first_air_date")
    val firstAirDate: String,

    @field:SerializedName("poster_path")
    val posterPath: String?,

    @field:SerializedName("backdrop_path")
    val backdropPath: String?,

    @field:SerializedName("vote_average")
    val voteAverage: Double,

    )
