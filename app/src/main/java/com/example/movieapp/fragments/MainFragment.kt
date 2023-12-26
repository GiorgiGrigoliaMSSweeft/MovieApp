package com.example.movieapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.AppViewModel
import com.example.movieapp.R
import com.example.movieapp.adapters.ViewPagerWithTitleAdapter
import com.example.movieapp.databinding.MainFragmentBinding

class MainFragment : Fragment() {
    private val binding by lazy { MainFragmentBinding.inflate(layoutInflater) }
    private val viewModel: AppViewModel by viewModels()
    private val viewPagerWithTitleAdapter by lazy {
        ViewPagerWithTitleAdapter(
            resources.getDimension(R.dimen.viewpager_next_item_visible),
            resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainRecyclerView.adapter = viewPagerWithTitleAdapter
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.allItems.observe(viewLifecycleOwner) {
            it.let { viewPagerWithTitleAdapter.submitList(it) }
        }

    }
}