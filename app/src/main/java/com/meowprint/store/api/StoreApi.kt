package com.meowprint.store.api

import com.meowprint.store.model.product.*
import okhttp3.MultipartBody
import retrofit2.http.*

interface StoreApi {
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
        @Body body: PatchImagesRequest
    ): Product

    @Multipart
    @POST("upload/image")
    suspend fun uploadImages(
        @Part parts: List<MultipartBody.Part>
    ): List<ImageResource>


}
