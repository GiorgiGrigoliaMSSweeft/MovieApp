package com.example.movieapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.network.NetworkModule.LatestMoviesApi
import com.example.movieapp.network.NetworkModule.LatestSeriesApi
import com.example.movieapp.network.NetworkModule.TrendingTodayApi
import com.example.movieapp.network.model.LatestMoviesItem
import com.example.movieapp.network.model.LatestSeriesItem
import com.example.movieapp.network.model.TrendingTodayItem
import com.example.movieapp.network.statuses.LatestMoviesApiStatus
import com.example.movieapp.network.statuses.LatestSeriesApiStatus
import com.example.movieapp.network.statuses.TrendingTodayApiStatus
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {

    // Latest Movies - Status
    private val _latestMoviesStatus = MutableLiveData<LatestMoviesApiStatus>()
    val latestMoviesStatus: LiveData<LatestMoviesApiStatus> = _latestMoviesStatus

    // Latest Series - Status
    private val _latestSeriesStatus = MutableLiveData<LatestSeriesApiStatus>()
    val latestSeriesStatus: LiveData<LatestSeriesApiStatus> = _latestSeriesStatus

    // Trending Today - Status
    private val _trendingTodayStatus = MutableLiveData<TrendingTodayApiStatus>()
    val trendingTodayStatus: LiveData<TrendingTodayApiStatus> = _trendingTodayStatus

    // Latest Movies
    private val _retrievedLatestMovies = MutableLiveData<List<LatestMoviesItem>>()
    val retrievedLatestMovies: LiveData<List<LatestMoviesItem>> = _retrievedLatestMovies

    // Latest Series
    private val _retrievedLatestSeries = MutableLiveData<List<LatestSeriesItem>>()
    val retrievedLatestSeries: LiveData<List<LatestSeriesItem>> = _retrievedLatestSeries

    // Trending Today
    private val _retrievedTrendingTodayItems = MutableLiveData<List<TrendingTodayItem>>()
    val retrievedTrendingTodayItems: LiveData<List<TrendingTodayItem>> =
        _retrievedTrendingTodayItems

    fun getLatestMovies() {
        viewModelScope.launch {
            _latestMoviesStatus.value = LatestMoviesApiStatus.LOADING
            try {
                val latestMovies = LatestMoviesApi.retrofitService.getLatestMovies()

                // Modifies the poster path to include the base URL
                val moviesWithFullPosterPath = latestMovies.results.map { movie ->
                    movie.copy(posterPath = POSTER_BASE_URL + movie.posterPath)
                }

                _retrievedLatestMovies.value = moviesWithFullPosterPath
                _latestMoviesStatus.value = LatestMoviesApiStatus.DONE
                Log.d("Tag", retrievedLatestMovies.value.toString())
            } catch (e: Exception) {
                _latestMoviesStatus.value = LatestMoviesApiStatus.ERROR
                _retrievedLatestMovies.value = listOf()
            }
        }
    }


    fun getLatestSeries() {
        viewModelScope.launch {
            _latestSeriesStatus.value = LatestSeriesApiStatus.LOADING
            try {
                val latestSeries = LatestSeriesApi.retrofitService.getLatestSeries()

                // Modifies the poster path to include the base URL
                val seriesWithFullPosterPath = latestSeries.results.map { series ->
                    series.copy(posterPath = POSTER_BASE_URL + series.posterPath)
                }

                _retrievedLatestSeries.value = seriesWithFullPosterPath
                _latestSeriesStatus.value = LatestSeriesApiStatus.DONE
                Log.d("Tag", retrievedLatestSeries.value.toString())
            } catch (e: Exception) {
                _latestSeriesStatus.value = LatestSeriesApiStatus.ERROR
                _retrievedLatestSeries.value = listOf()
            }
        }
    }

    fun getTrendingTodayItems() {
        viewModelScope.launch {
            _trendingTodayStatus.value = TrendingTodayApiStatus.LOADING
            try {
                val trendingTodayItems = TrendingTodayApi.retrofitService.getLatestTrendingItems()

                // Modifies the poster path to include the base url
                val trendingTodayItemsWithFullPosterPath = trendingTodayItems.results.map { trendingTodayItem ->
                    trendingTodayItem.copy(posterPath = POSTER_BASE_URL + trendingTodayItem.posterPath)
                }

                _retrievedTrendingTodayItems.value = trendingTodayItemsWithFullPosterPath
                _trendingTodayStatus.value = TrendingTodayApiStatus.DONE
                Log.d("Tag", retrievedTrendingTodayItems.value.toString())
            } catch (e: Exception) {
                _trendingTodayStatus.value = TrendingTodayApiStatus.ERROR
                _retrievedTrendingTodayItems.value = listOf()
            }
        }
    }

    companion object {
        private const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"
    }
}
