package com.example.movieapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import com.example.movieapp.R
import com.example.movieapp.databinding.RecyclerViewItemWithTitleBinding
import com.example.movieapp.databinding.ViewPagerWithTitleBinding
import com.example.movieapp.rvmodel.RvItemDataClass
import kotlin.math.abs

class RecyclerViewAdapter(
    private val nextItemVisiblePx: Float,
    private val currentItemHorizontalMarginPx: Float
) : ListAdapter<RvItemDataClass, RecyclerViewHolder>(ViewPagerWithTitleDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return when (viewType) {
            R.layout.view_pager_with_title -> RecyclerViewHolder.ViewPagerItemViewHolder(
                ViewPagerWithTitleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            R.layout.recycler_view_item_with_title -> RecyclerViewHolder.RecyclerViewViewHolder(
                RecyclerViewItemWithTitleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> throw IllegalArgumentException("Invalid ViewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        when (holder) {
            is RecyclerViewHolder.ViewPagerItemViewHolder ->
                holder.bind(getItem(position))

            is RecyclerViewHolder.RecyclerViewViewHolder ->
                holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when {
            item is RvItemDataClass && item.isViewPagerType -> R.layout.view_pager_with_title
            item is RvItemDataClass && item.isRecyclerViewType -> R.layout.recycler_view_item_with_title
            else -> throw IllegalArgumentException("Invalid item type at position $position")
        }
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

sealed class RecyclerViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    class ViewPagerItemViewHolder(private val binding: ViewPagerWithTitleBinding) : RecyclerViewHolder(binding) {
        private val viewPagerItemAdapter = ViewPagerItemAdapter()
        fun bind(item: RvItemDataClass) {
            binding.viewPagerTitle.text = item.title
            binding.viewPager.adapter = viewPagerItemAdapter
            viewPagerItemAdapter.submitList(item.listOfItems)

            setupViewPager()
        }

        private fun setupViewPager() {
            // Number of off-screen pages to retain on either side of the current page

            val nextItemVisiblePx = binding.root.resources.getDimension(R.dimen.viewpager_next_item_visible)
            val currentItemHorizontalMarginPx = binding.root.resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
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

    class RecyclerViewViewHolder(private val binding: RecyclerViewItemWithTitleBinding) :
        RecyclerViewHolder(binding) {
        private val recyclerViewItemAdapter = RecyclerViewItemAdapter()
        fun bind(item: RvItemDataClass) {
            binding.recyclerViewTitle.text = item.title
            binding.recyclerView.adapter = recyclerViewItemAdapter
            recyclerViewItemAdapter.submitList(item.listOfItems)
        }
    }
}