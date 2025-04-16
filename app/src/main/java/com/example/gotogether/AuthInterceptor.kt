package com.example.gotogether

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = sharedPreferences.getString("auth_token", "dbb62adc-d975-4724-bf1a-c7c2de9dc908")
        if (token.isNullOrEmpty()) {
            throw Exception("No token available")
        }

        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer dbb62adc-d975-4724-bf1a-c7c2de9dc908")
            .build()
        return chain.proceed(newRequest)
    }
}