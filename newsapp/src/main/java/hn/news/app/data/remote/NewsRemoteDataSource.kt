package hn.news.app.data.remote

import hn.news.app.BuildConfig
import hn.news.app.data.model.Article
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(
    private val apiService: NewsApiService
) {
    suspend fun fetchTopHeadlines(
        country: String = "us",
        apiKey: String = BuildConfig.API_KEY,
    ): List<Article> {
        val response = apiService.getTopHeadlines(country, apiKey)
        return response.articles
    }

    suspend fun queryNews(
        query: String = "btc",
        apiKey: String = BuildConfig.API_KEY,
    ): List<Article> {
        val response = apiService.queryNews(query, apiKey)
        return response.articles
    }
}