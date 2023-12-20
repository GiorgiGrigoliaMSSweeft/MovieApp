package com.example.movieapp.network.model

import com.squareup.moshi.Json

data class LatestMoviesResponse(
    val results: List<LatestMoviesItem>
)

data class LatestMoviesItem(
    val id: Int,
    @Json(name = "poster_path") val posterPath: String
)