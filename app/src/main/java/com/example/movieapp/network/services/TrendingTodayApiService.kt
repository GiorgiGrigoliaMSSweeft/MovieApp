package com.example.movieapp.network.services

import com.example.movieapp.network.NetworkModule
import com.example.movieapp.network.model.TrendingTodayResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TrendingTodayApiService {
    @GET("trending/all/day")
    suspend fun getLatestTrendingItems(
        @Header("Authorization") apiKey: String = "Bearer ${NetworkModule.API_KEY}"
    ): TrendingTodayResponse
}