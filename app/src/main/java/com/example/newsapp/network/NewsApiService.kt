package com.example.newsapp.network

import com.example.newsapp.features.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    suspend fun getAllNews(
        @Query("country") country: String = "us",
        @Query("page") page: Int,
        @Query("category") category: String = "technology",
        //@Query("q") searchText: String = "",
    ): NewsResponse

    //https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=1016049e4f6b4eb88ab6840bb811a649
    //https://newsapi.org/v2/top-headlines?country=us&category=technology&apiKey=1016049e4f6b4eb88ab6840bb811a649
}