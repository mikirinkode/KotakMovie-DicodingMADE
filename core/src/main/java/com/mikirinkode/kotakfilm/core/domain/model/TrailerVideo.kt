package com.mikirinkode.kotakfilm.core.domain.model

data class TrailerVideo(
    val trailerId: String,
    val key: String,
    val name: String,
    val site: String,
    val type: String,
    val official: Boolean
)
