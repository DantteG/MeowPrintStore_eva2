package com.meowprint.store.model

data class LoginRequest(val email: String, val password: String)

data class LoginResponse(
    val authToken: String? = null,
    val token: String? = null,
    val user: UserResponse? = null
)

data class UserResponse(
    val id: Int?,
    val name: String?,
    val email: String?
)
