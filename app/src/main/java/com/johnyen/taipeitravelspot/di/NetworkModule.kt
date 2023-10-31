package com.johnyen.taipeitravelspot.di


import com.johnyen.taipeitravelspot.BuildConfig
import com.johnyen.taipeitravelspot.api.portal.TaipeiOpenDataService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

@InstallIn(
    SingletonComponent::class,
    ActivityRetainedComponent::class,
    ActivityComponent::class,
    FragmentComponent::class
)
@Module
class NetworkModule {
    companion object {
        const val TIMEOUT = 30L
        lateinit var requestBody: Request.Builder
        val networkInterceptor = Interceptor { chain ->
            val requestBuilder: Request.Builder = chain.request().newBuilder()
            requestBuilder.header("Content-Type", "application/json")
            requestBuilder.header("Accept", "application/json")
            requestBody = requestBuilder
            chain.proceed(requestBuilder.build())
        }

        val interceptor = HttpLoggingInterceptor().apply {
            when (BuildConfig.DEBUG) {
                true -> setLevel(HttpLoggingInterceptor.Level.BODY)
                false -> setLevel(HttpLoggingInterceptor.Level.NONE)
            }
        }
    }

    @Provides
    fun provideTaipeiOpenService(): TaipeiOpenDataService{
        return TaipeiOpenDataService.create()
    }
}
