package com.example.newsapp.features.repository

import com.example.newsapp.features.model.Article
import com.example.newsapp.network.NewsApiService
import com.example.newsapp.utils.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepository @Inject constructor(private val apiService: NewsApiService) {

    fun getAllNews(): Flow<NetworkResponse<List<Article>>> = flow {
        try {
            emit(NetworkResponse.Loading)
            val response = apiService.getAllNews()
            emit(NetworkResponse.Success(response.articles))
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.toString()))
        }
    }
}