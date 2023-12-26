package com.example.movieapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.movieapp.databinding.ViewPagerWithTitleBinding
import com.example.movieapp.rvmodel.RvItemDataClass
import kotlin.math.abs

class ViewPagerWithTitleAdapter(
    private val nextItemVisiblePx: Float,
    private val currentItemHorizontalMarginPx: Float
) : ListAdapter<RvItemDataClass, ViewPagerWithTitleAdapter.ViewHolder>(ViewPagerWithTitleDiffCallback()) {

    inner class ViewHolder(private val binding: ViewPagerWithTitleBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewPagerItemAdapter = ViewPagerItemAdapter()
        fun bind(item: RvItemDataClass) {
            binding.viewPagerTitle.text = item.title
            binding.viewPager.adapter = viewPagerItemAdapter
            viewPagerItemAdapter.submitList(item.listOfItems)
        }

        private fun setupViewPager() {
            // Number of off-screen pages to retain on either side of the current page
            binding.viewPager.offscreenPageLimit = 1

            // Calculate translationX based on dimensions
            val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx

            // Create a custom page transformer
            val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
                // Apply translationX and scaleY transformations
                page.translationX = -pageTranslationX * position
                page.scaleY = 1 - (0.25f * abs(position))
            }

            // Set the custom page transformer
            binding.viewPager.setPageTransformer(pageTransformer)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewPagerWithTitleBinding.inflate(
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

class ViewPagerWithTitleDiffCallback : DiffUtil.ItemCallback<RvItemDataClass>() {
    override fun areItemsTheSame(oldItem: RvItemDataClass, newItem: RvItemDataClass): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: RvItemDataClass, newItem: RvItemDataClass): Boolean {
        return oldItem.title == newItem.title
    }
}