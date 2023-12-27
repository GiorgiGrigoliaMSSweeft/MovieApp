package com.example.movieapp.model

import com.example.movieapp.network.model.Item

data class MainRecyclerViewItem(
    val title: String,
    val listOfItems: List<Item>,
    val isViewPagerType: Boolean,
    val isRecyclerViewType: Boolean
)