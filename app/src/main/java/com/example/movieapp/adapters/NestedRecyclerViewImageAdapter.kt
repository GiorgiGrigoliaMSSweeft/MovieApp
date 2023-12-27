package com.example.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movieapp.databinding.NestedRecyclerViewImageLayoutBinding
import com.example.movieapp.diffutils.NestedRecyclerViewImageDiffUtil
import com.example.movieapp.network.model.Item

class NestedRecyclerViewImageAdapter : ListAdapter<Item, NestedRecyclerViewImageAdapter.ViewHolder>(NestedRecyclerViewImageDiffUtil()) {
    inner class ViewHolder(private val binding: NestedRecyclerViewImageLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.nesterRecyclerViewImage.load(item.posterPath)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NestedRecyclerViewImageLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }
}