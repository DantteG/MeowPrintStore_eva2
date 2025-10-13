package com.meowprint.store.model.auth

data class LoginResponse(
    val authToken: String? = null,
    val token: String? = null,
    val user: UserResponse? = null
)
