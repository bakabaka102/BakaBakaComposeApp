package hn.single.network.remote

import hn.single.network.Constants
import hn.single.network.remote.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET(Constants.QUERY_EVERYTHING_URL)
    suspend fun queryNews(
        @Query("q")
        query: String,
        @Query("apiKey")
        apiKey: String,
    ): NewsResponse

    @GET(Constants.QUERY_TOP_HEAD_LINES)
    suspend fun getTopHeadlines(
        @Query("country")
        country: String = "us",
        @Query("apiKey")
        apiKey: String,
    ): NewsResponse
}