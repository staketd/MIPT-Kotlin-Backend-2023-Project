package edu.phystech.kotlin.backend.api.user.model

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginRequest(
    val login: String,
    val password: String
)
