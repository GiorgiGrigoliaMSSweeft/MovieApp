package com.example.movieapp.network

import com.example.movieapp.constants.Constants.BASE_URL
import com.example.movieapp.network.services.MovieDetailsService
import com.example.movieapp.network.services.MovieListService
import com.example.movieapp.network.services.SeriesDetailsService
import com.example.movieapp.network.services.SeriesListService
import com.example.movieapp.network.services.TrendingTodayService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkModule {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    object Movies {
        val retrofitService: MovieListService by lazy {
            retrofit.create(MovieListService::class.java)
        }
    }

    object Series {
        val retrofitService: SeriesListService by lazy {
            retrofit.create(SeriesListService::class.java)
        }
    }

    object TrendingToday {
        val retrofitService: TrendingTodayService by lazy {
            retrofit.create(TrendingTodayService::class.java)
        }
    }

    object MovieDetails {
        val retrofitService: MovieDetailsService by lazy {
            retrofit.create(MovieDetailsService::class.java)
        }
    }

    object SeriesDetails {
        val retrofitService: SeriesDetailsService by lazy {
            retrofit.create(SeriesDetailsService::class.java)
        }
    }
}
