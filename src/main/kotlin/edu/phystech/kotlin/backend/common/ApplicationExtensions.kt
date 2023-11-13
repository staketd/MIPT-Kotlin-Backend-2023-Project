package edu.phystech.kotlin.backend.common

import io.ktor.server.config.*
import io.ktor.server.config.ConfigLoader.Companion.load


val config = ConfigLoader.load()

fun getJWTSettings() = JWTSettings(
    config.property("jwt.secret").getString(),
    config.property("jwt.audience").getString(),
    config.property("jwt.issuer").getString(),
    config.property("jwt.realm").getString()
)