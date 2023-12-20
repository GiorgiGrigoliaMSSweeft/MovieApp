package com.example.movieapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.network.NetworkModule.TrendingTodayApi
import com.example.movieapp.network.NetworkModule.LatestSeriesApi
import com.example.movieapp.network.model.LatestMoviesItem
import com.example.movieapp.network.NetworkModule.LatestMoviesApi
import com.example.movieapp.network.model.LatestSeriesItem
import com.example.movieapp.network.model.TrendingTodayItem
import kotlinx.coroutines.launch

enum class LatestMoviesApiStatus { LOADING, ERROR, DONE }
enum class LatestSeriesApiStatus { LOADING, ERROR, DONE }
enum class TrendingTodayApiStatus { LOADING, ERROR, DONE }

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
                _retrievedLatestMovies.value = latestMovies.results
                _latestMoviesStatus.value = LatestMoviesApiStatus.DONE
                Log.d("Tag", "Successfully retrieved latest movies")
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
                _retrievedLatestSeries.value = latestSeries.results
                _latestSeriesStatus.value = LatestSeriesApiStatus.DONE
                Log.d("Tag", "Successfully retrieved latest series")
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
                _retrievedTrendingTodayItems.value = trendingTodayItems.results
                _trendingTodayStatus.value = TrendingTodayApiStatus.DONE
                Log.d("Tag", "Successfully retrieved todays trending items")
            } catch (e: Exception) {
                _trendingTodayStatus.value = TrendingTodayApiStatus.ERROR
                _retrievedTrendingTodayItems.value = listOf()
            }
        }
    }
}
