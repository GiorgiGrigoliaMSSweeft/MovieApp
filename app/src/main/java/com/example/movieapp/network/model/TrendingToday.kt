package com.example.movieapp.network.model

import com.squareup.moshi.Json

data class TrendingTodayResponse(
    val results: List<TrendingTodayItem>
)

data class TrendingTodayItem(
    val id: Int,
    @Json(name = "poster_path") val posterPath: String
)