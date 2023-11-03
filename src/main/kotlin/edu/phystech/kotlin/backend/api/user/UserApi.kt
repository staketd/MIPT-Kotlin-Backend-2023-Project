package edu.phystech.kotlin.backend.api.user

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import edu.phystech.kotlin.backend.api.user.model.UserLoginRequest
import edu.phystech.kotlin.backend.api.user.model.UserRegistrationRequest
import edu.phystech.kotlin.backend.common.JWT_AUDIENCE
import edu.phystech.kotlin.backend.common.JWT_ISSUER
import edu.phystech.kotlin.backend.common.JWT_SECRET
import edu.phystech.kotlin.backend.common.exception.ValidationException
import edu.phystech.kotlin.backend.repository.user.UserRepository
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Routing.userRoute() {
    val maxLoginLength = 100
    val userRepository by inject<UserRepository>()

    post("/user/register") {
        val userRegistrationRequest = call.receive<UserRegistrationRequest>()

        if (userRegistrationRequest.login.length > maxLoginLength) {
            throw ValidationException("Login is too long: max length is $maxLoginLength")
        }

        userRepository.registerUser(
            userRegistrationRequest.login,
            userRegistrationRequest.name,
            userRegistrationRequest.password
        )

        call.respondText("Success!")
    }

    post("/user/login") {
        val userLoginRequest = call.receive<UserLoginRequest>()

        userRepository.validateLoginPassword(userLoginRequest.login, userLoginRequest.password)

        val token = JWT.create()
            .withAudience(JWT_AUDIENCE)
            .withIssuer(JWT_ISSUER)
            .withClaim("login", userLoginRequest.login)
            .withExpiresAt(Date(System.currentTimeMillis() + 600_000))
            .sign(Algorithm.HMAC256(JWT_SECRET))

        call.respond(hashMapOf("token" to token))
    }
}