package com.example.newsapp.repository

import com.example.newsapp.di.NewsApiService
import com.example.newsapp.model.NewsResponse
import javax.inject.Inject

class NewsRepository @Inject constructor(private val apiService: NewsApiService) {

    suspend fun getAllNews(): NewsResponse {
        return apiService.getAllNews()
    }
}