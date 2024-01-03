package com.example.movieapp.fragments.mainfragment.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.example.movieapp.model.MainRecyclerViewItem

class MainRecyclerViewDiffUtil : DiffUtil.ItemCallback<MainRecyclerViewItem>() {
    override fun areItemsTheSame(oldItem: MainRecyclerViewItem, newItem: MainRecyclerViewItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MainRecyclerViewItem, newItem: MainRecyclerViewItem): Boolean {
        return oldItem.title == newItem.title
    }
}