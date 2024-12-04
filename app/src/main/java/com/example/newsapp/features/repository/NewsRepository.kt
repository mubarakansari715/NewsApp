package com.example.newsapp.features.repository

import android.util.Log
import com.example.newsapp.features.model.Article
import com.example.newsapp.features.model.ArticleDbModel
import com.example.newsapp.network.NewsApiService
import com.example.newsapp.room.ArticleDao
import com.example.newsapp.utils.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val apiService: NewsApiService,
    private val articleDao: ArticleDao
) {

    fun getAllNews(
        page: Int,
        category: String,
        searchText: String
    ): Flow<NetworkResponse<List<Article>>> = flow {
        try {
            emit(NetworkResponse.Loading)
            val response =
                apiService.getAllNews(page = page, category = category, searchText = searchText)
            Log.e("TAG", "==> Network response : $response", )
            emit(NetworkResponse.Success(response.articles))
        } catch (e: Exception) {
            Log.e("TAG", "==> Network response Failed : $e", )
            emit(NetworkResponse.Error(e.message.toString()))
        }
    }

    suspend fun insertDbData(article: ArticleDbModel) = articleDao.insert(article)

    suspend fun getAllNewsDb() = articleDao.getAllHeadlineData()
}