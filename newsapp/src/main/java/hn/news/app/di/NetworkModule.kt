package hn.news.app.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hn.news.app.BuildConfig
import hn.news.app.data.Constants
import hn.single.network.InternetAvailabilityRepository
import hn.single.network.NetworkStatusTracker
import hn.single.network.NoInternetInterceptor
import hn.single.network.remote.NewsApiService
import java.util.concurrent.TimeUnit
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkStatusTracker(
        @ApplicationContext context: Context
    ): NetworkStatusTracker {
        return NetworkStatusTracker(context)
    }

    @Provides
    @Singleton
    fun provideInternetAvailabilityRepository(
        networkStatusTracker: NetworkStatusTracker
    ): InternetAvailabilityRepository {
        return InternetAvailabilityRepository(networkStatusTracker)
    }

    @Provides
    @Singleton
    fun provideNoInternetInterceptor(
        internetAvailabilityRepository: InternetAvailabilityRepository
    ): NoInternetInterceptor {
        return NoInternetInterceptor(internetAvailabilityRepository)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(noInternetInterceptor: NoInternetInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY // LOG TOÀN BỘ request/response
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor(noInternetInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideJson(): Json = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun provideNewsApiService(
        okHttpClient: OkHttpClient,
        json: Json
    ): NewsApiService {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(okHttpClient)
            .build()
            .create(NewsApiService::class.java)
    }
}