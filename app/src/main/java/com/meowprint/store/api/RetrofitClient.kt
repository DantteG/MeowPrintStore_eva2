    package com.meowprint.store.api

    import android.content.Context
    import com.google.gson.GsonBuilder
    import okhttp3.OkHttpClient
    import okhttp3.logging.HttpLoggingInterceptor
    import retrofit2.Retrofit
    import retrofit2.converter.gson.GsonConverterFactory

    object RetrofitClient {
        fun authRetrofit(): Retrofit {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            val client = OkHttpClient.Builder().addInterceptor(logger).build()
            return Retrofit.Builder()
                .baseUrl(Constants.AUTH_BASE)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .client(client)
                .build()
        }

        fun storeRetrofit(context: Context): Retrofit {
            val tm = TokenManager(context)
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor { tm.getToken() })
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(Constants.STORE_BASE)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .client(client)
                .build()
        }
    }
