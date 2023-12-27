package com.example.movieapp.model

import com.example.movieapp.network.model.Item

data class MainRecyclerViewItem(
    val title: String,
    val listOfItems: List<Item>,
    val isViewPagerType: Boolean, // true if it's a ViewPager type, false otherwise
    val isRecyclerViewType: Boolean // true if it's a RecyclerView type, false otherwise
)