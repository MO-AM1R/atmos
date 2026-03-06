package com.example.atmos.data.interceptor
import com.example.atmos.utils.AppConstants
import jakarta.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response


class ApiKeyInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        val newUrl = request.url.newBuilder()
            .addQueryParameter("appid", AppConstants.WEATHER_API_KEY)
            .build()

        val newRequest = request.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}