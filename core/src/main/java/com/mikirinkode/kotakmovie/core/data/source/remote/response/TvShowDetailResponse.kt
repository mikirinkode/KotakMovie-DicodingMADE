package com.mikirinkode.kotakmovie.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TvShowDetailResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("first_air_date")
    val firstAirDate: String,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("tagline")
    val tagline: String?,

    @field:SerializedName("genres")
    val genres: List<GenresItem>,

    @field:SerializedName("episode_run_time")
    val episodeRunTime: List<Int>,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    )


