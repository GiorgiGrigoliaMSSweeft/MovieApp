package com.example.movieapp.network.model

import com.squareup.moshi.Json

data class ItemList(
    val results: List<Item>
)

data class Item(
    val id: Int,
    @Json(name = "poster_path") val posterPath: String
)