package com.example.newsapp.di

import com.example.newsapp.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    suspend fun getAllNews(
        @Query("country") country: String = "us"
    ): NewsResponse
}