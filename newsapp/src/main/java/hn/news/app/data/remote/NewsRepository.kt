package hn.news.app.data.remote

import hn.news.app.BuildConfig
import hn.single.network.remote.model.Article
import hn.single.network.ApiResult
import hn.single.network.InternetAvailabilityRepository
import hn.single.network.remote.NewsRemoteDataSource
import hn.single.network.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val remoteDataSource: NewsRemoteDataSource
) {
    @Inject
    lateinit var internetRepository: InternetAvailabilityRepository
    suspend fun getTopHeadlines(
        country: String = "us",
        apiKey: String = BuildConfig.API_KEY,
    ): List<Article> = withContext(Dispatchers.IO) {
        remoteDataSource.fetchTopHeadlines(country, apiKey)
    }

    suspend fun queryNews(
        query: String = "btc",
        apiKey: String = BuildConfig.API_KEY,
    ): ApiResult<List<Article>> {
        return safeApiCall(internetRepository) {
            remoteDataSource.queryNews(
                query = query,
                apiKey = apiKey,
            )
        }
    }

}