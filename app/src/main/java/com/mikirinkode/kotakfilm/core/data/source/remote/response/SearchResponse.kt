package com.mikirinkode.kotakfilm.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(
	@field:SerializedName("results")
	val results: List<SearchResultsItem>,
)

data class SearchResultsItem(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String?,

	@field:SerializedName("overview")
	val overview: String,

	@field:SerializedName("release_date")
	val releaseDate: String?,

	@field:SerializedName("poster_path")
	val posterPath: String?,

	@field:SerializedName("backdrop_path")
	val backdropPath: String?,

	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@field:SerializedName("name")
	val name: String?,

	@field:SerializedName("first_air_date")
	val firstAirDate: String?,

	@field:SerializedName("media_type")
	val mediaType: String,
)
