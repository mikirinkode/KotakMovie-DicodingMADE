package com.mikirinkode.kotakmovie.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GenresItem(
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String
)