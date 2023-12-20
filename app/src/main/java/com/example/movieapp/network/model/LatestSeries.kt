package com.example.movieapp.network.model

import com.squareup.moshi.Json

data class LatestSeriesResponse(
    val results: List<LatestSeriesItem>
)

data class LatestSeriesItem(
    val id: Int,
    @Json(name = "poster_path") val posterPath: String
)