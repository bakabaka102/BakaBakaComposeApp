package hn.news.app.data

import hn.news.app.BuildConfig

object Constants {
    const val API_KEY = BuildConfig.API_KEY //"your api key"
    //BuildConfig.API_KEY
    //https://newsapi.org/v2/everything?q=bitcoin&apiKey=86b39afce9f54f57befb44ea9ecfc357
    const val BASE_URL = "https://newsapi.org/"
    const val QUERY_EVERYTHING_URL = "v2/everything"

    //https://newsapi.org/v2/top-headlines?country=vi&apiKey=86b39afce9f54f57befb44ea9ecfc357
    const val QUERY_TOP_HEAD_LINES = "v2/top-headlines"

}