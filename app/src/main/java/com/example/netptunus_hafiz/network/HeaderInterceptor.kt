package com.example.netptunus_hafiz.network

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .header("apikey", SupabaseConfig.SUPABASE_KEY)
            .header("Authorization", "Bearer ${SupabaseConfig.SUPABASE_KEY}")
            .header("Accept-Profile", SupabaseConfig.SCHEMA_NAME)
            .header("Content-Profile", SupabaseConfig.SCHEMA_NAME)
            .header("Prefer", "return=representation")

        return chain.proceed(requestBuilder.build())
    }
}
