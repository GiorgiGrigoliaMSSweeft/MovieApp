package com.example.movieapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.constants.Constants.LATEST_MOVIES
import com.example.movieapp.constants.Constants.LATEST_SERIES
import com.example.movieapp.constants.Constants.POSTER_BASE_URL
import com.example.movieapp.constants.Constants.TRENDING_TODAY
import com.example.movieapp.model.MainRecyclerViewItem
import com.example.movieapp.network.NetworkModule.Movies
import com.example.movieapp.network.NetworkModule.Series
import com.example.movieapp.network.NetworkModule.TrendingToday
import com.example.movieapp.network.model.Item
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {
    private val _allItemsRequestStatus = MutableLiveData<Result<List<Item>>>()
    val allItemsRequestStatus: MutableLiveData<Result<List<Item>>> = _allItemsRequestStatus

    private val _allItems = MutableLiveData<List<MainRecyclerViewItem>>()
    val allItems: LiveData<List<MainRecyclerViewItem>> = _allItems

    private val _latestMovies = MutableLiveData<List<Item>>()
    private val _latestSeries = MutableLiveData<List<Item>>()
    private val _trendingItems = MutableLiveData<List<Item>>()

    init {
        fetchAllItems()
    }

    fun fetchAllItems() {
        viewModelScope.launch {
            try {
                fetchLatestMovies()
                fetchLatestSeries()
                fetchTrendingTodayItems()

                _allItems.value = listOf(
                    MainRecyclerViewItem(
                        LATEST_MOVIES,
                        _latestMovies.value.orEmpty(),
                        isViewPagerType = true,
                        isRecyclerViewType = false
                    ),
                    MainRecyclerViewItem(
                        LATEST_SERIES,
                        _latestSeries.value.orEmpty(),
                        isViewPagerType = true,
                        isRecyclerViewType = false
                    ),
                    MainRecyclerViewItem(
                        TRENDING_TODAY,
                        _trendingItems.value.orEmpty(),
                        isViewPagerType = false,
                        isRecyclerViewType = true
                    )
                )
            } catch (e: Exception) {
                _allItemsRequestStatus.value = Result.failure(e)
            }
        }
    }

    private suspend fun fetchLatestMovies() {
        return try {
            val latestMovies =
                Movies.retrofitService.getMovies().results.map { movie ->
                    movie.copy(posterPath = POSTER_BASE_URL + movie.posterPath)
                }
            _latestMovies.value = latestMovies
            _allItemsRequestStatus.value = Result.success(latestMovies)
        } catch (e: Exception) {
            _allItemsRequestStatus.value = Result.failure(e)
        }
    }


    private suspend fun fetchLatestSeries() {
        try {
            val latestSeries =
                Series.retrofitService.getSeries().results.map { series ->
                    series.copy(posterPath = POSTER_BASE_URL + series.posterPath)
                }
            _latestSeries.value = latestSeries
            _allItemsRequestStatus.value = Result.success(latestSeries)
        } catch (e: Exception) {
            _allItemsRequestStatus.value = Result.failure(e)
        }
    }

    private suspend fun fetchTrendingTodayItems() {
        try {
            val trendingTodayItems =
                TrendingToday.retrofitService.getTrendingItems().results.map { trendingTodayItem ->
                    trendingTodayItem.copy(posterPath = POSTER_BASE_URL + trendingTodayItem.posterPath)
                }
            _trendingItems.value = trendingTodayItems
            _allItemsRequestStatus.value = Result.success(trendingTodayItems)
        } catch (e: Exception) {
            _allItemsRequestStatus.value = Result.failure(e)
        }
    }
}