package hn.news.app.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hn.news.app.data.model.Article
import hn.news.app.data.remote.NewsRepository
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {
    var newsList: List<Article> by mutableStateOf(emptyList())
        private set
    var isLoading by mutableStateOf(false)
        private set
    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        fetchNews()
    }

    fun fetchNews() {
        viewModelScope.launch {
            isLoading = true
            try {
                newsList = repository.getTopHeadlines()
                errorMessage = null
            } catch (e: Exception) {
                Log.e("NewsViewModel", "Error fetching news: ${e.message}")
                errorMessage = "Có lỗi xảy ra: ${e.message}"
            }
            isLoading = false
        }
    }
}