package com.meowprint.store.api

import com.meowprint.store.model.LoginRequest
import com.meowprint.store.model.auth.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body body: LoginRequest): LoginResponse
}
