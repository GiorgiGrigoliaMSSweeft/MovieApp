package com.example.movieapp.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import com.example.movieapp.R
import com.example.movieapp.adapters.NestedRecyclerViewImageAdapter
import com.example.movieapp.adapters.NestedViewPagerImageAdapter
import com.example.movieapp.databinding.NestedRecyclerViewItemLayoutBinding
import com.example.movieapp.databinding.NestedViewPagerItemLayoutBinding
import com.example.movieapp.model.MainRecyclerViewItem
import kotlin.math.abs

sealed class ViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    class NestedViewPagerItemViewHolder(private val binding: NestedViewPagerItemLayoutBinding) : ViewHolder(binding) {
        private val viewPagerImageAdapter = NestedViewPagerImageAdapter()
        fun bind(item: MainRecyclerViewItem) {
            binding.nestedViewPagerTitle.text = item.title
            binding.nestedViewPager.adapter = viewPagerImageAdapter
            viewPagerImageAdapter.submitList(item.listOfItems)

            setupViewPager()
        }

        private fun setupViewPager() {
            // Number of off-screen pages to retain on either side of the current page

            val nextItemVisiblePx = binding.root.resources.getDimension(R.dimen.viewpager_next_item_visible)
            val currentItemHorizontalMarginPx = binding.root.resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
            binding.nestedViewPager.offscreenPageLimit = 1
            // Calculate translationX based on dimensions
            val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx

            // Create a custom page transformer
            val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
                // Apply translationX and scaleY transformations
                page.translationX = -pageTranslationX * position
                page.scaleY = 1 - (0.25f * abs(position))
            }
            // Set the custom page transformer
            binding.nestedViewPager.setPageTransformer(pageTransformer)
        }
    }

    class NestedRecyclerViewViewHolder(private val binding: NestedRecyclerViewItemLayoutBinding) : ViewHolder(binding) {
        private val nestedRecyclerViewImageAdapter = NestedRecyclerViewImageAdapter()
        fun bind(item: MainRecyclerViewItem) {
            binding.nestedRecyclerViewTitle.text = item.title
            binding.nestedRecyclerView.adapter = nestedRecyclerViewImageAdapter
            nestedRecyclerViewImageAdapter.submitList(item.listOfItems)
        }
    }
}