package com.johnyen.taipeitravelspot.api.portal


import com.johnyen.taipeitravelspot.api.portal.response.TaipeiDataResponse
import com.johnyen.taipeitravelspot.di.NetworkModule
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

import java.util.concurrent.TimeUnit


interface TaipeiOpenDataService {
    @GET("{lang}/Attractions/All")
    suspend fun getAttractionAll(@Path("lang")lang:String
    ): TaipeiDataResponse

    companion object {

        fun create(
        ): TaipeiOpenDataService {
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(NetworkModule.TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(NetworkModule.networkInterceptor)
                .addInterceptor(NetworkModule.interceptor)
                .addInterceptor { chain ->
                    chain.proceed(
                        chain.request()
                            .newBuilder()
                            .addHeader("Accept","application/json")
                            .build()
                    )
                }
                .build()

            return Retrofit.Builder()
                .baseUrl("https://www.travel.taipei/open-api/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TaipeiOpenDataService::class.java)
        }
    }
}