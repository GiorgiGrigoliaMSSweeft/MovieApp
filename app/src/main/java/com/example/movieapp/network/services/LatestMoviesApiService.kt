package com.example.movieapp.network.services

import com.example.movieapp.network.model.LatestMoviesResponse
import com.example.movieapp.network.NetworkModule.API_KEY
import retrofit2.http.GET
import retrofit2.http.Header

interface LatestMovieApiService {
    @GET("movie/now_playing")
    suspend fun getLatestMovies(
        @Header("Authorization") apiKey: String = "Bearer $API_KEY"
    ): LatestMoviesResponse
}