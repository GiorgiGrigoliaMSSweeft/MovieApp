package com.example.movieapp.rvmodel

import com.example.movieapp.network.model.Item

data class RvItemDataClass(
    val title: String,
    val listOfItems: List<Item>
)