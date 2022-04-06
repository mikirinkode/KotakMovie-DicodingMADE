package com.mikirinkode.kotakfilm.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class TrailerVideoResponse(
	@field:SerializedName("results")
	val results: List<TrailerItem>
)

data class TrailerItem(


	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("site")
	val site: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("official")
	val official: Boolean,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("key")
	val key: String
)
