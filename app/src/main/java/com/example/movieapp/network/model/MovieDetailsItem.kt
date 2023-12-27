package com.example.movieapp.network.model

import com.squareup.moshi.Json

data class MovieDetailsItem(
    @Json(name = "production_companies") val productionCompanies: List<ProductionCompany>,
    val overview: String,
    val homepage: String
)

data class ProductionCompany(
    val id: Int,
    val name: String,
    @Json(name = "origin_country") val originCountry: String
)