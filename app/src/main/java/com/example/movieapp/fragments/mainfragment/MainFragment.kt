package com.example.movieapp.fragments.mainfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapp.AppViewModel
import com.example.movieapp.databinding.MainFragmentBinding
import com.example.movieapp.fragments.mainfragment.adapters.MainRecyclerViewAdapter
import com.example.movieapp.fragments.mainfragment.adapters.NestedViewPagerItemClickListener
import com.example.movieapp.network.model.Item

class MainFragment: Fragment(), NestedViewPagerItemClickListener {
    private val binding by lazy { MainFragmentBinding.inflate(layoutInflater) }
    private val viewModel: AppViewModel by viewModels()
    private val mainRecyclerViewAdapter by lazy { MainRecyclerViewAdapter(this) }

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

        viewModel.allItemsRequestStatus.observe(viewLifecycleOwner) { result ->
            result.let {
                it.fold(
                    onSuccess = {
                        binding.apply {
                            mainRecyclerView.visibility = View.VISIBLE
                            errorImage.visibility = View.GONE
                            errorMessage.visibility = View.GONE
                            tryAgainButton.visibility = View.GONE
                        }
                    },
                    onFailure = {
                        binding.apply {
                            mainRecyclerView.visibility = View.GONE
                            errorImage.visibility = View.VISIBLE
                            errorMessage.visibility = View.VISIBLE
                            tryAgainButton.visibility = View.VISIBLE
                            tryAgainButton.setOnClickListener {
                                viewModel.fetchAllItems()
                            }
                        }
                    }
                )
            }
        }
    }

    override fun onViewPagerItemClick(item: Item) {
        // TODO
    }
}