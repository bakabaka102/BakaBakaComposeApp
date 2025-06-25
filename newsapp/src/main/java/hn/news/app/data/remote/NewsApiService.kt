package hn.news.app.data.remote

import hn.news.app.BuildConfig
import hn.news.app.data.Constants
import hn.news.app.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET(Constants.QUERY_EVERYTHING_URL)
    suspend fun queryNews(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
    ): NewsResponse

    @GET(Constants.QUERY_TOP_HEAD_LINES)
    suspend fun getTopHeadlines(
        @Query("country") country: String = "vi",
        @Query("apiKey") apiKey: String,
    ): NewsResponse
}