package edu.phystech.kotlin.backend.api.user

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import edu.phystech.kotlin.backend.api.user.model.UserLoginRequest
import edu.phystech.kotlin.backend.api.user.model.UserRegistrationRequest
import edu.phystech.kotlin.backend.common.JWT_AUDIENCE
import edu.phystech.kotlin.backend.common.JWT_ISSUER
import edu.phystech.kotlin.backend.common.JWT_SECRET
import edu.phystech.kotlin.backend.service.UserService
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Routing.userRoute() {
    val userService by inject<UserService>()

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

        val token = JWT.create()
            .withAudience(JWT_AUDIENCE)
            .withIssuer(JWT_ISSUER)
            .withClaim("login", userLoginRequest.login)
            .withExpiresAt(Date(System.currentTimeMillis() + 600_000))
            .sign(Algorithm.HMAC256(JWT_SECRET))

        call.respond(hashMapOf("token" to token))
    }
}