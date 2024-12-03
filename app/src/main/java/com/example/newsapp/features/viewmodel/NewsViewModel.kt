package com.example.newsapp.features.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.features.model.Article
import com.example.newsapp.features.repository.NewsRepository
import com.example.newsapp.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repository: NewsRepository) : ViewModel() {

    private val _newsHeadlineState = MutableStateFlow<NewsHeadlineState>(NewsHeadlineState.Loading)
    val newsHeadlineState: StateFlow<NewsHeadlineState> = _newsHeadlineState

    fun getAllNews() {
        repository.getAllNews().onEach { result ->
            when (result) {
                is NetworkResponse.Loading -> {
                    _newsHeadlineState.value = NewsHeadlineState.Loading
                }

                is NetworkResponse.Error -> {
                    _newsHeadlineState.value = NewsHeadlineState.Error(result.errorMessage)
                }

                is NetworkResponse.Success -> {
                    result.result?.let {
                        _newsHeadlineState.value = NewsHeadlineState.Success(it)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}

sealed class NewsHeadlineState() {
    object Loading : NewsHeadlineState()
    data class Error(val message: String) : NewsHeadlineState()
    data class Success(val data: List<Article>) : NewsHeadlineState()
}