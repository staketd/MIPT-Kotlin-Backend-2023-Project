package edu.phystech.kotlin.backend.model.user

import java.time.Instant

data class User(
    val login: String,
    val passwordHashed: ByteArray,
    val name: String,
    val registrationTime: Instant
)
