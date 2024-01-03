package com.example.movieapp.fragments.mainfragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.movieapp.R
import com.example.movieapp.constants.Constants.INVALID_VIEW_TYPE
import com.example.movieapp.constants.Constants.INVALID_VIEW_TYPE_AT_POSITION
import com.example.movieapp.databinding.NestedRecyclerViewItemLayoutBinding
import com.example.movieapp.databinding.NestedViewPagerItemLayoutBinding
import com.example.movieapp.fragments.mainfragment.diffutils.MainRecyclerViewDiffUtil
import com.example.movieapp.model.MainRecyclerViewItem
import com.example.movieapp.viewholder.ViewHolder

class MainRecyclerViewAdapter(
    private val nestedViewPagerImageClickListener: NestedViewPagerItemClickListener
) : ListAdapter<MainRecyclerViewItem, ViewHolder>(MainRecyclerViewDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            R.layout.nested_view_pager_item_layout -> ViewHolder.NestedViewPagerViewHolder(
                NestedViewPagerItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                nestedViewPagerImageClickListener
            )

            R.layout.nested_recycler_view_item_layout -> ViewHolder.NestedRecyclerViewViewHolder(
                NestedRecyclerViewItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> throw IllegalArgumentException(INVALID_VIEW_TYPE)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder.NestedViewPagerViewHolder ->
                holder.bind(getItem(position))

            is ViewHolder.NestedRecyclerViewViewHolder ->
                holder.bind(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when {
            item is MainRecyclerViewItem && item.isViewPagerType -> R.layout.nested_view_pager_item_layout
            item is MainRecyclerViewItem && item.isRecyclerViewType -> R.layout.nested_recycler_view_item_layout
            else -> throw IllegalArgumentException("$INVALID_VIEW_TYPE_AT_POSITION $position")
        }
    }
}