package hn.news.app.data.remote

import hn.news.app.data.model.Article
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val remoteDataSource: NewsRemoteDataSource
) {
    suspend fun getTopHeadlines(): List<Article> = remoteDataSource.fetchTopHeadlines()
}