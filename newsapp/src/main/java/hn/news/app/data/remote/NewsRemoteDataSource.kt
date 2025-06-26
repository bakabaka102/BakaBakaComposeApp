package hn.news.app.data.remote

import hn.news.app.data.model.Article
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(
    private val apiService: NewsApiService
) {
    suspend fun fetchTopHeadlines(): List<Article> {
        val response = apiService.getTopHeadlines()
        return response.articles
    }
}