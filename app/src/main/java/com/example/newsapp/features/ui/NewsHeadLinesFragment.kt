package com.example.newsapp.features.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewsHeadLinesBinding
import com.example.newsapp.features.adapter.NewsHeadlineAdapter
import com.example.newsapp.features.viewmodel.NewsHeadlineState
import com.example.newsapp.features.viewmodel.NewsViewModel
import com.example.newsapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class NewsHeadLinesFragment : Fragment() {

    private lateinit var binding: FragmentNewsHeadLinesBinding
    private lateinit var mAdapter: NewsHeadlineAdapter
    private val viewModel: NewsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsHeadLinesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initial()
    }

    private fun initial() {
        setupRecyclerview()
        collectUiState()
        fetchHeadline()
        pagination()
    }

    private fun fetchHeadline() {
        if (Constants.isNetworkConnectionAvailable(requireContext())) {
            viewModel.resetPagination()
            viewModel.getAllNews()
        } else {
            Toast.makeText(requireContext(), "Please the internet connection!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun setupRecyclerview() {
        mAdapter = NewsHeadlineAdapter()
        binding.rvNewsHeadline.adapter = mAdapter
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.newsHeadlineState.collect { result ->
                when (result) {
                    is NewsHeadlineState.Loading -> {

                    }

                    is NewsHeadlineState.Error -> {
                        showPaginationLoader(false)
                    }

                    is NewsHeadlineState.Success -> {
                        val data = result.data
                        showPaginationLoader(false)
                        mAdapter.setData(data)
                    }
                }
            }
        }
    }


    private fun pagination() {
        binding.rvNewsHeadline.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItems = layoutManager.childCount ?: 0
                val totalItemCount = layoutManager.itemCount ?: 0
                val pastVisibility = layoutManager.findLastCompletelyVisibleItemPosition()
                if (dy > 0) {
                    if (visibleItems + pastVisibility >= totalItemCount) {
                        // start your pagination
                        if (viewModel.pagination.loading.not()) {
                            viewModel.pagination.loading = true
                            showPaginationLoader(true)
                            viewModel.getAllNews()
                        }
                    }
                }
            }
        })
    }

    fun showPaginationLoader(isShow: Boolean) {
        if (isShow) {
            binding.progressCircular.visibility = View.VISIBLE
        } else {
            viewModel.setLoading(false)
            binding.progressCircular.visibility = View.GONE
        }
    }

}