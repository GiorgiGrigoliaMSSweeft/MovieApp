package com.example.movieapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.AppViewModel
import com.example.movieapp.adapters.MainRecyclerViewAdapter
import com.example.movieapp.databinding.MainFragmentWithRecyclerViewBinding
import com.example.movieapp.network.statuses.RequestStatus

class MainFragmentWithRecyclerView : Fragment() {
    private val binding by lazy { MainFragmentWithRecyclerViewBinding.inflate(layoutInflater) }
    private val viewModel: AppViewModel by viewModels()
    private val mainRecyclerViewAdapter by lazy { MainRecyclerViewAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainRecyclerView.adapter = mainRecyclerViewAdapter
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.allItems.observe(viewLifecycleOwner) {
            it.let { mainRecyclerViewAdapter.submitList(it) }
        }

        viewModel.requestStatus.observe(viewLifecycleOwner) {
            it.let {
                if (it == RequestStatus.ERROR) {
                    binding.apply {
                        mainRecyclerView.visibility = View.GONE
                        errorImage.visibility = View.VISIBLE
                        errorMessage.visibility = View.VISIBLE
                        tryAgainButton.visibility = View.VISIBLE
                        tryAgainButton.setOnClickListener {
                            viewModel.fetchAllItems()
                        }
                    }
                } else {
                    binding.apply {
                        mainRecyclerView.visibility = View.VISIBLE
                        errorImage.visibility = View.GONE
                        errorMessage.visibility = View.GONE
                        tryAgainButton.visibility = View.GONE
                    }
                }
            }
        }
    }
}