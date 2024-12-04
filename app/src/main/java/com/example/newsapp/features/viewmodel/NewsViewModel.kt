package com.example.newsapp.features.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.features.model.Article
import com.example.newsapp.features.model.toArticleLocalToArticle
import com.example.newsapp.features.model.toArticleToArticleLocal
import com.example.newsapp.features.repository.NewsRepository
import com.example.newsapp.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    // LiveData to store the last selected option
    val lastSelectedOption: MutableLiveData<String> = MutableLiveData("Technology")

    private val _newsHeadlineState = MutableStateFlow<NewsHeadlineState>(NewsHeadlineState.Loading)
    val newsHeadlineState: StateFlow<NewsHeadlineState> = _newsHeadlineState

    val pagination = PaginationState()
    fun updatePage() = pagination.pageNumber++
    fun resetPagination() {
        pagination.dataList.clear()
        pagination.pageNumber = 1
    }

    fun setLoading(value: Boolean) {
        pagination.loading = value
    }

    fun getAllNews() {
        Log.e("TAG", "getAllNews: lastSelectedOption ${lastSelectedOption.value}", )
        repository.getAllNews(pagination.pageNumber, lastSelectedOption.value.orEmpty()).onEach { result ->
            when (result) {
                is NetworkResponse.Loading -> {
                    _newsHeadlineState.value = NewsHeadlineState.Loading
                }

                is NetworkResponse.Error -> {
                    _newsHeadlineState.value = NewsHeadlineState.Error(result.errorMessage)
                }

                is NetworkResponse.Success -> {
                    result.result?.let {
                        updatePage()
                        pagination.dataList.addAll(it)
                        _newsHeadlineState.value = NewsHeadlineState.Success(pagination.dataList)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun resetState() {
        _newsHeadlineState.value = NewsHeadlineState.Loading
    }

    fun insertData(article: Article) {
        viewModelScope.launch {
            repository.insertDbData(article.toArticleToArticleLocal())
        }
    }

    fun getAllArticleDb() {
        viewModelScope.launch {
            val result = repository.getAllNewsDb()
            val dataList: List<Article> = result.map { it.toArticleLocalToArticle() }
            pagination.dataList = dataList.toMutableList()
            resetState()
            _newsHeadlineState.value = NewsHeadlineState.Success(pagination.dataList)
        }
    }
}

sealed class NewsHeadlineState() {
    object Loading : NewsHeadlineState()
    data class Error(val message: String) : NewsHeadlineState()
    data class Success(val data: List<Article>) : NewsHeadlineState()
}

data class PaginationState(
    var dataList: MutableList<Article> = mutableListOf(),
    var pageNumber: Int = 1,
    var loading: Boolean = false,
)