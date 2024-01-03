package com.example.movieapp.network.services

import com.example.movieapp.constants.Constants.API_KEY
import com.example.movieapp.network.model.ItemDetails
import com.example.movieapp.network.model.ItemList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("movie/now_playing")
    suspend fun getLatestMovies(
        @Query("api_key") apiKey: String = API_KEY
    ): ItemList

    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path("id") movieId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): ItemDetails
}

interface MovieListService : MovieService

interface MovieDetailsService : MovieService
