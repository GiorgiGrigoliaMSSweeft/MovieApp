package com.example.movieapp.network.services

import com.example.movieapp.network.NetworkModule.API_KEY
import com.example.movieapp.network.model.ItemList
import retrofit2.http.GET
import retrofit2.http.Query

interface TrendingTodayApiService {
    @GET("trending/all/day")
    suspend fun getLatestTrendingItems(
        @Query("api_key") apiKey: String = API_KEY
    ): ItemList
}