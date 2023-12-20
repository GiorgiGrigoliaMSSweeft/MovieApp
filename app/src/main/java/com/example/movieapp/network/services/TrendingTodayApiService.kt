package com.example.movieapp.network.services

import com.example.movieapp.network.NetworkModule.API_KEY
import com.example.movieapp.network.model.TrendingTodayResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface TrendingTodayApiService {
    @GET("trending/all/day")
    suspend fun getLatestTrendingItems(
        @Header("Authorization") apiKey: String = "Bearer $API_KEY"
    ): TrendingTodayResponse
}