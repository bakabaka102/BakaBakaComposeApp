package hn.single.network.remote

import hn.single.network.BuildConfig
import hn.single.network.remote.model.Article
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

    suspend fun queryNewsBTC(
        query: String = "btc",
        apiKey: String = BuildConfig.API_KEY,
    ): List<Article> {
        val response = apiService.queryNews(query, apiKey)
        return response.articles
    }

    suspend fun queryNewsETH(
        query: String = "eth",
        apiKey: String = BuildConfig.API_KEY,
    ): List<Article> {
        val response = apiService.queryNews(query, apiKey)
        return response.articles
    }
}