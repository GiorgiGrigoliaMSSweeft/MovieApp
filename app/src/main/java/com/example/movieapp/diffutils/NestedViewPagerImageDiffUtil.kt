package com.example.movieapp.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.example.movieapp.network.model.Item

class NestedViewPagerImageDiffUtil : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }
}