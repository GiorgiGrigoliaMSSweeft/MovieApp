package com.example.movieapp.fragments.mainfragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movieapp.R
import com.example.movieapp.databinding.NestedViewPagerImageLayoutBinding
import com.example.movieapp.fragments.mainfragment.diffutils.NestedViewPagerImageDiffUtil
import com.example.movieapp.network.model.Item

interface NestedViewPagerItemClickListener {
    fun onViewPagerItemClick(item: Item)
}

class NestedViewPagerImageAdapter(
    private val itemClickListener: NestedViewPagerItemClickListener
) : ListAdapter<Item, NestedViewPagerImageAdapter.ViewHolder>(NestedViewPagerImageDiffUtil()) {
    inner class ViewHolder(private val binding: NestedViewPagerImageLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.nestedViewPagerImage.load(item.posterPath) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }

            binding.nestedViewPagerImage.let {
                it.setOnClickListener { itemClickListener.onViewPagerItemClick(item) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NestedViewPagerImageLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }
}