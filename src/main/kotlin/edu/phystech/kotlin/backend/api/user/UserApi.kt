package edu.phystech.kotlin.backend.api.user

import edu.phystech.kotlin.backend.api.user.model.UserLoginRequest
import edu.phystech.kotlin.backend.api.user.model.UserRegistrationRequest
import edu.phystech.kotlin.backend.common.getJWTSettings
import edu.phystech.kotlin.backend.service.UserService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.userRoute() {
    val userService by inject<UserService>()

    val jwtSettings = getJWTSettings()

    post("/user/register") {
        val userRegistrationRequest = call.receive<UserRegistrationRequest>()

        userService.registerUser(
            userRegistrationRequest.login,
            userRegistrationRequest.name,
            userRegistrationRequest.password
        )

        call.respondText("Success!")
    }

    post("/user/login") {
        val userLoginRequest = call.receive<UserLoginRequest>()

        userService.validateLoginPassword(userLoginRequest.login, userLoginRequest.password)

        val token = jwtSettings.generateToken(userLoginRequest.login, 600_000)

        call.respond(hashMapOf("token" to token))
    }
}