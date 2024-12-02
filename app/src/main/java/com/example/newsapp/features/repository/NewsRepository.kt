package com.example.newsapp.features.repository

import com.example.newsapp.network.NewsApiService
import com.example.newsapp.features.model.NewsResponse
import javax.inject.Inject

class NewsRepository @Inject constructor(private val apiService: NewsApiService) {

    suspend fun getAllNews(): NewsResponse {
        return apiService.getAllNews()
    }
}