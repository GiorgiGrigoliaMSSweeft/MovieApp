package com.example.movieapp.network.model

import com.squareup.moshi.Json

data class ItemDetails(
    @Json(name = "production_companies") val productionCompanies: List<ProductionCompany>? = null,
    val overview: String? = null,
    val homepage: String? = null
)

data class ProductionCompany(
    val id: Int,
    val name: String,
    @Json(name = "origin_country") val originCountry: String
)