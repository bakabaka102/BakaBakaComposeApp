package hn.news.app.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hn.news.app.BuildConfig
import hn.news.app.data.model.Article
import hn.news.app.data.network.ApiResult
import hn.news.app.data.network.UIState
import hn.news.app.data.remote.NewsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {
    var newsUiState: UIState<List<Article>> by mutableStateOf(UIState.Loading)
        private set

    var newsQuery: UIState<List<Article>> by mutableStateOf(UIState.Loading)
        private set

    fun fetchNews() {
        viewModelScope.launch {
            newsUiState = UIState.Loading
            try {
                val data = repository.getTopHeadlines()
                newsUiState = UIState.Success(data)
            } catch (e: Exception) {
                Log.e("NewsViewModel", "Error fetching news: ${e.message}")
                newsUiState = UIState.Failure(e)
            }
        }
    }

    fun queryNews(
        query: String,
        apiKey: String = BuildConfig.API_KEY,
    ) {
        viewModelScope.launch {
            newsQuery = UIState.Loading
            runCatching {
                val result = repository.queryNews(query, apiKey).also {
                    Log.d("NewsViewModel", "Fetched news by common Api: $it")
                }
                newsQuery = when (result) {
                    is ApiResult.Success -> {
                        UIState.Success(result.data)
                    }

                    is ApiResult.Error -> {
                        UIState.Failure(Throwable(result.message))
                    }
                }
            }.onFailure {
                Log.e("NewsViewModel", "Fetched news by common Api: ${it.message}")
                newsQuery = UIState.Failure(it)
            }
        }
    }
}