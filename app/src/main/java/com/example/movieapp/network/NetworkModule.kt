package com.example.movieapp.network

import com.example.movieapp.network.services.LatestMovieApiService
import com.example.movieapp.network.services.LatestSeriesApiService
import com.example.movieapp.network.services.TrendingTodayApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkModule {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY =
        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4NzYwYTgzYjY0ZGU2NDc4ZjMzNzk3MTI5NDRmMjU0ZCIsInN1YiI6IjY1ODJhYjBiYjM0NDA5NDY4YzFhZTY3YiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.VVr8JZxvcAkFsOPnFmFDn_zAA5K1h3Tq3aE16OE8BWw"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    // Latest Movies API Service
    object LatestMoviesApi {
        val retrofitService: LatestMovieApiService by lazy {
            retrofit.create(LatestMovieApiService::class.java)
        }
    }

    // Latest Series API Service
    object LatestSeriesApi{
        val retrofitService: LatestSeriesApiService by lazy {
            retrofit.create(LatestSeriesApiService::class.java)
        }
    }

    // Trending Today API Service
    object TrendingTodayApi{
        val retrofitService: TrendingTodayApiService by lazy {
            retrofit.create(TrendingTodayApiService::class.java)
        }
    }
}
