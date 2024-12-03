package com.example.newsapp.features.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
    private val viewModel: NewsViewModel by viewModels()

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
    }

    private fun fetchHeadline() {
        if (Constants.isNetworkConnectionAvailable(requireContext())) {
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

                    }

                    is NewsHeadlineState.Success -> {
                        val data = result.data
                        mAdapter.setData(data)
                    }
                }
            }
        }
    }

}