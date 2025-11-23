package com.meowprint.store.model.auth

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("created_at") val createdAt: Long? = null,
    @SerializedName("role") val role: String? = null
)
