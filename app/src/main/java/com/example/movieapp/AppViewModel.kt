package com.example.movieapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.MainRecyclerViewItem
import com.example.movieapp.network.NetworkModule.LatestMoviesApi
import com.example.movieapp.network.NetworkModule.LatestSeriesApi
import com.example.movieapp.network.NetworkModule.POSTER_BASE_URL
import com.example.movieapp.network.NetworkModule.TrendingTodayApi
import com.example.movieapp.network.model.Item
import com.example.movieapp.network.statuses.RequestStatus
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {
    // Request Status
    private val _requestStatus = MutableLiveData<RequestStatus>()
    val requestStatus: LiveData<RequestStatus> = _requestStatus

    // Latest Movies
    private val _retrievedLatestMovies = MutableLiveData<List<Item>>()

    // Latest Series
    private val _retrievedLatestSeries = MutableLiveData<List<Item>>()

    // Trending Today
    private val _retrievedTrendingTodayItems = MutableLiveData<List<Item>>()

    // All Items
    private val _allItems = MutableLiveData<List<MainRecyclerViewItem>>()
    val allItems: LiveData<List<MainRecyclerViewItem>> = _allItems

    init { fetchAllItems() }

    fun fetchAllItems() {
        viewModelScope.launch {
            try {
                // Call functions to fetch each type of item separately
                fetchLatestMovies()
                fetchLatestSeries()
                fetchTrendingTodayItems()

                // Update allItems LiveData with the updated lists
                _allItems.value = listOf(
                    MainRecyclerViewItem(LATEST_MOVIES, _retrievedLatestMovies.value.orEmpty(), isViewPagerType = true, isRecyclerViewType = false),
                    MainRecyclerViewItem(LATEST_SERIES, _retrievedLatestSeries.value.orEmpty(), isViewPagerType = true, isRecyclerViewType = false),
                    MainRecyclerViewItem(TRENDING_TODAY, _retrievedTrendingTodayItems.value.orEmpty(), isViewPagerType = false, isRecyclerViewType = true)
                )
            } catch (e: Exception) {
                Log.e("Tag", "Error fetching all items: ${e.message}")
            }
        }
    }

    private suspend fun fetchLatestMovies() {
        try {
            val latestMovies = LatestMoviesApi.retrofitService.getLatestMovies()
            _retrievedLatestMovies.value = latestMovies.results.map { movie ->
                movie.copy(posterPath = POSTER_BASE_URL + movie.posterPath)
            }
            _requestStatus.value = RequestStatus.SUCCESS
        } catch (e: Exception) {
            _requestStatus.value = RequestStatus.ERROR
        }
    }

    private suspend fun fetchLatestSeries() {
        try {
            val latestSeries = LatestSeriesApi.retrofitService.getLatestSeries()
            _retrievedLatestSeries.value = latestSeries.results.map { series ->
                series.copy(posterPath = POSTER_BASE_URL + series.posterPath)
            }
            _requestStatus.value = RequestStatus.SUCCESS
        } catch (e: Exception) {
            _requestStatus.value = RequestStatus.ERROR
        }
    }

    private suspend fun fetchTrendingTodayItems() {
        try {
            val trendingTodayItems = TrendingTodayApi.retrofitService.getLatestTrendingItems()
            _retrievedTrendingTodayItems.value = trendingTodayItems.results.map { trendingTodayItem ->
                trendingTodayItem.copy(posterPath = POSTER_BASE_URL + trendingTodayItem.posterPath)
            }
            _requestStatus.value = RequestStatus.SUCCESS
        } catch (e: Exception) {
            _requestStatus.value = RequestStatus.ERROR
        }
    }

    companion object {
        private const val LATEST_MOVIES = "Latest Movies"
        private const val LATEST_SERIES = "Latest Series"
        private const val TRENDING_TODAY = "Trending Today"
    }
}