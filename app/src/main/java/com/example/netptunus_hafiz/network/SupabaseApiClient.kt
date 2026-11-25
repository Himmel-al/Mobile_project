package com.example.netptunus_hafiz.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object SupabaseApiClient {
    private var retrofit: Retrofit? = null

    fun getService(): SupabaseApiService {
        val baseUrl = if (SupabaseConfig.BASE_URL.endsWith("/")) {
            SupabaseConfig.BASE_URL
        } else {
            "${SupabaseConfig.BASE_URL}/"
        }

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        val instance = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit = instance
        return instance.create(SupabaseApiService::class.java)
    }
}
