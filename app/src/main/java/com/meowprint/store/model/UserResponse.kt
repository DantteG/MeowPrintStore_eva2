package com.meowprint.store.model.auth

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    // El campo "created_at" también se puede incluir si lo necesitas
    @SerializedName("created_at")
    val createdAt: Long
)
