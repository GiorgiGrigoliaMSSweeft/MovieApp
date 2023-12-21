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
    const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"
    const val API_KEY = "8760a83b64de6478f3379712944f254d"

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
