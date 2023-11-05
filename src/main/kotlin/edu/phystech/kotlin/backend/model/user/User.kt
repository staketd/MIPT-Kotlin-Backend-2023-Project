package edu.phystech.kotlin.backend.model.user

import kotlinx.datetime.Instant

data class User(
    val login: String,
    val passwordHashed: ByteArray,
    val name: String,
    val registrationTime: Instant
)
