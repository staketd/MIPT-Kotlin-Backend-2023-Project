package edu.phystech.kotlin.backend.api.user.model

import kotlinx.serialization.Serializable

@Serializable
data class UserRegistrationRequest(
    val login: String,
    val password: String,
    val name: String
)
