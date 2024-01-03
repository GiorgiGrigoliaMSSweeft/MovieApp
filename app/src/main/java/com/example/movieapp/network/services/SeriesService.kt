package com.example.movieapp.network.services

import com.example.movieapp.constants.Constants.API_KEY
import com.example.movieapp.network.model.ItemDetails
import com.example.movieapp.network.model.ItemList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SeriesService {
    @GET("tv/popular")
    suspend fun getSeries(
        @Query("api_key") apiKey: String = API_KEY
    ): ItemList

    @GET("tv/{id}")
    suspend fun getSeriesDetails(
        @Path("id") seriesId: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): ItemDetails
}

interface SeriesListService : SeriesService

interface SeriesDetailsService : SeriesService