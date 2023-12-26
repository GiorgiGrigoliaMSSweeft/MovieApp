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
import com.example.movieapp.adapters.RecyclerViewAdapter
import com.example.movieapp.databinding.MainFragmentBinding
import com.example.movieapp.network.statuses.LatestMoviesApiStatus
import com.example.movieapp.network.statuses.LatestSeriesApiStatus
import com.example.movieapp.network.statuses.TrendingTodayApiStatus

class MainFragment : Fragment() {
    private val binding by lazy { MainFragmentBinding.inflate(layoutInflater) }
    private val viewModel: AppViewModel by viewModels()
    private val recyclerViewAdapter by lazy {
        RecyclerViewAdapter(
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

        binding.mainRecyclerView.adapter = recyclerViewAdapter
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.allItems.observe(viewLifecycleOwner) {
            it.let { recyclerViewAdapter.submitList(it) }
        }

        viewModel.latestMoviesStatus.observe(viewLifecycleOwner) {
            it.let {
                if (it == LatestMoviesApiStatus.ERROR) {
                    binding.apply {
                        mainRecyclerView.visibility = View.GONE
                        errorMessage.visibility = View.VISIBLE
                        tryAgainButton.visibility = View.VISIBLE
                        tryAgainButton.setOnClickListener {
                            viewModel.getAllItems()
                        }
                    }
                } else {
                    binding.apply {
                        mainRecyclerView.visibility = View.VISIBLE
                        errorMessage.visibility = View.GONE
                        tryAgainButton.visibility = View.GONE
                    }
                }
            }
        }

        viewModel.latestSeriesStatus.observe(viewLifecycleOwner) {
            it.let {
                if (it == LatestSeriesApiStatus.ERROR) {
                    binding.apply {
                        mainRecyclerView.visibility = View.GONE
                        errorMessage.visibility = View.VISIBLE
                        tryAgainButton.visibility = View.VISIBLE
                        tryAgainButton.setOnClickListener {
                            viewModel.getAllItems()
                        }
                    }
                } else {
                    binding.apply {
                        mainRecyclerView.visibility = View.VISIBLE
                        errorMessage.visibility = View.GONE
                        tryAgainButton.visibility = View.GONE
                    }
                }
            }
        }

        viewModel.trendingTodayStatus.observe(viewLifecycleOwner) {
            it.let {
                if (it == TrendingTodayApiStatus.ERROR) {
                    binding.apply {
                        mainRecyclerView.visibility = View.GONE
                        errorMessage.visibility = View.VISIBLE
                        tryAgainButton.visibility = View.VISIBLE
                        tryAgainButton.setOnClickListener {
                            viewModel.getAllItems()
                        }
                    }
                } else {
                    binding.apply {
                        mainRecyclerView.visibility = View.VISIBLE
                        errorMessage.visibility = View.GONE
                        tryAgainButton.visibility = View.GONE
                    }
                }
            }
        }
    }
}