package com.example.movieapp.network.services

import com.example.movieapp.network.NetworkModule.API_KEY
import com.example.movieapp.network.model.ItemList
import retrofit2.http.GET
import retrofit2.http.Query

interface LatestMovieApiService {
    @GET("movie/now_playing")
    suspend fun getLatestMovies(
        @Query("api_key") apiKey: String = API_KEY
    ): ItemList
}