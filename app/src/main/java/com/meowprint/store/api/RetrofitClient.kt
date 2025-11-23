package com.meowprint.store.api

import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // Base URL para productos (tienda)
    private const val STORE_BASE = "https://x8ki-letl-twmt.n7.xano.io/api:Bcv1qHHX/"

    // Base URL para usuarios (auth)
    private const val USER_BASE = "https://x8ki-letl-twmt.n7.xano.io/api:eg9IqgHd/"

    fun storeRetrofit(context: Context): Retrofit {
        val tm = TokenManager(context)
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor { tm.getToken() })
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl(STORE_BASE)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(client)
            .build()
    }

    fun userRetrofit(context: Context): Retrofit {
        val tm = TokenManager(context)
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor { tm.getToken() })
            .addInterceptor(logger)
            .build()

        return Retrofit.Builder()
            .baseUrl(USER_BASE)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(client)
            .build()
    }
}
