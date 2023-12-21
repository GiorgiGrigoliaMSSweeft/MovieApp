package com.example.movieapp.network.services

import com.example.movieapp.network.NetworkModule.API_KEY
import com.example.movieapp.network.model.LatestSeriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LatestSeriesApiService {
    @GET("tv/popular")
    suspend fun getLatestSeries(
        @Query("api_key") apiKey: String = API_KEY
    ): LatestSeriesResponse
}