package com.example.movieapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.network.NetworkModule.LatestMoviesApi
import com.example.movieapp.network.NetworkModule.LatestSeriesApi
import com.example.movieapp.network.NetworkModule.POSTER_BASE_URL
import com.example.movieapp.network.NetworkModule.TrendingTodayApi
import com.example.movieapp.network.model.Item
import com.example.movieapp.network.statuses.LatestMoviesApiStatus
import com.example.movieapp.network.statuses.LatestSeriesApiStatus
import com.example.movieapp.network.statuses.TrendingTodayApiStatus
import com.example.movieapp.model.MainRecyclerViewItem
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
    private val _retrievedLatestMovies = MutableLiveData<List<Item>>()

    // Latest Series
    private val _retrievedLatestSeries = MutableLiveData<List<Item>>()

    // Trending Today
    private val _retrievedTrendingTodayItems = MutableLiveData<List<Item>>()

    // All Items
    private val _allItems = MutableLiveData<List<MainRecyclerViewItem>>()
    val allItems: LiveData<List<MainRecyclerViewItem>> = _allItems

    init {
        getAllItems()
    }

    fun getAllItems() {
        viewModelScope.launch {
            try {
                // Call functions to fetch each type of item separately
                val latestMovies = fetchLatestMovies()
                val latestSeries = fetchLatestSeries()
                val trendingTodayItems = fetchTrendingTodayItems()

                _retrievedLatestMovies.value = latestMovies
                _retrievedLatestSeries.value = latestSeries
                _retrievedTrendingTodayItems.value = trendingTodayItems

                _allItems.value = listOf(
                    MainRecyclerViewItem(LATEST_MOVIES, latestMovies, isViewPagerType = true, isRecyclerViewType = false),
                    MainRecyclerViewItem(LATEST_SERIES, latestSeries, isViewPagerType = true, isRecyclerViewType = false),
                    MainRecyclerViewItem(TRENDING_TODAY, trendingTodayItems, isViewPagerType = false, isRecyclerViewType = true)
                )
            } catch (e: Exception) {
                // Handle error
                Log.e("Tag", "Error fetching all items: ${e.message}")
            }
        }
    }

    private suspend fun fetchLatestMovies(): List<Item> {
        _latestMoviesStatus.value = LatestMoviesApiStatus.LOADING
        try {
            val latestMovies = LatestMoviesApi.retrofitService.getLatestMovies()
            return latestMovies.results.map { movie ->
                movie.copy(posterPath = POSTER_BASE_URL + movie.posterPath)
            }
        } catch (e: Exception) {
            _latestMoviesStatus.value = LatestMoviesApiStatus.ERROR
            _retrievedLatestMovies.value = listOf()
            throw e
        }
    }

    private suspend fun fetchLatestSeries(): List<Item> {
        _latestSeriesStatus.value = LatestSeriesApiStatus.LOADING
        try {
            val latestSeries = LatestSeriesApi.retrofitService.getLatestSeries()
            return latestSeries.results.map { series ->
                series.copy(posterPath = POSTER_BASE_URL + series.posterPath)
            }
        } catch (e: Exception) {
            _latestSeriesStatus.value = LatestSeriesApiStatus.ERROR
            _retrievedLatestSeries.value = listOf()
            throw e
        }
    }

    private suspend fun fetchTrendingTodayItems(): List<Item> {
        _trendingTodayStatus.value = TrendingTodayApiStatus.LOADING
        try {
            val trendingTodayItems = TrendingTodayApi.retrofitService.getLatestTrendingItems()
            return trendingTodayItems.results.map { trendingTodayItem ->
                trendingTodayItem.copy(posterPath = POSTER_BASE_URL + trendingTodayItem.posterPath)
            }
        } catch (e: Exception) {
            _trendingTodayStatus.value = TrendingTodayApiStatus.ERROR
            _retrievedTrendingTodayItems.value = listOf()
            throw e
        }
    }

    companion object {
        private const val LATEST_MOVIES = "Latest Movies"
        private const val LATEST_SERIES = "Latest Series"
        private const val TRENDING_TODAY = "Trending Today"
    }
}