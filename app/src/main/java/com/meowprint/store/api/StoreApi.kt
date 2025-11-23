package com.meowprint.store.api

import com.meowprint.store.model.LoginRequest
import com.meowprint.store.model.auth.LoginResponse
import com.meowprint.store.model.Product
import com.meowprint.store.model.product.CreateProductRequest
import com.meowprint.store.model.product.ImageResource
import com.meowprint.store.model.product.PatchImageRequest
import com.meowprint.store.model.user.User
import okhttp3.MultipartBody
import retrofit2.http.*

interface StoreApi {

    // ============================
    // 📦 Endpoints de Productos
    // ============================

    @GET("product")
    suspend fun listProducts(
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null,
        @Query("q") q: String? = null
    ): List<Product>

    @POST("product")
    suspend fun createProduct(@Body body: CreateProductRequest): Product

    @PATCH("product/{id}")
    suspend fun patchProductImages(
        @Path("id") id: Int,
        @Body body: PatchImageRequest
    ): Product

    @Multipart
    @POST("upload/image")
    suspend fun uploadImages(
        @Part parts: List<MultipartBody.Part>
    ): List<ImageResource>


    // ============================
    // 👥 Endpoints de Usuarios
    // ============================

    // 🔑 Devuelve el usuario autenticado (login + token)
    @GET("auth/me")
    suspend fun getMe(): User

    // 🔐 Devuelve todos los usuarios (solo admin)
    @GET("users")
    suspend fun getAllUsers(): List<User>

    @POST("auth/login")
    suspend fun login(@Body body: LoginRequest): LoginResponse

    @PATCH("users/{id}/block")
    suspend fun blockUser(@Path("id") id: Int): User

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: Int): Map<String, Any>
}
