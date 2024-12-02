package com.example.newsapp.features.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.features.repository.NewsRepository
import com.example.newsapp.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {


    fun getAllNews() {
        repository.getAllNews().onEach { result ->
            when (result) {
                is NetworkResponse.Loading -> {
                    Log.e("TAG", "getAllNews: Loading")
                }

                is NetworkResponse.Error -> {
                    Log.e("TAG", "getAllNews: Error")
                }

                is NetworkResponse.Success -> {
                    Log.e("TAG", "getAllNews: Success ${result.result}")
                }
            }
        }.launchIn(viewModelScope)
    }
}