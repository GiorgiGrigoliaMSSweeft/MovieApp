package com.example.movieapp.network.services

import com.example.movieapp.network.NetworkModule.API_KEY
import com.example.movieapp.network.model.LatestSeriesResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface LatestSeriesApiService {
    @GET("tv/popular")
    suspend fun getLatestSeries(
        @Header("Authorization") apiKey: String = "Bearer $API_KEY"
    ): LatestSeriesResponse
}