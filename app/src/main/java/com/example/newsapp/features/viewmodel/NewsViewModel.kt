package com.example.newsapp.features.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.features.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {


    fun getAllNews() {
        viewModelScope.launch {
            val response = repository.getAllNews()
            Log.e("TAG", "getAllNews: $response")
        }
    }
}