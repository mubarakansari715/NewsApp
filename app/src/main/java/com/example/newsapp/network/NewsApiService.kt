package com.example.newsapp.network

import com.example.newsapp.features.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    suspend fun getAllNews(
        @Query("country") country: String = "us"
    ): NewsResponse
}