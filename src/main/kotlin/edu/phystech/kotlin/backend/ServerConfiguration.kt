package edu.phystech.kotlin.backend

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import edu.phystech.kotlin.backend.common.*
import edu.phystech.kotlin.backend.common.exception.AccessDeniedException
import edu.phystech.kotlin.backend.common.exception.InvalidLoginException
import edu.phystech.kotlin.backend.common.exception.UserAlreadyExistsException
import edu.phystech.kotlin.backend.common.exception.ValidationException
import edu.phystech.kotlin.backend.modules.blogModule
import edu.phystech.kotlin.backend.modules.userModule
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import kotlinx.serialization.json.Json
import org.koin.ktor.plugin.Koin


fun Application.configureServer() {
    install(Koin) {
        modules(blogModule)
        modules(userModule)
    }
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
        })
    }
    configureStatusPages()
    configureJWT()

}

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, ErrorResponse("500: $cause"))
        }
        exception<ValidationException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, ErrorResponse(cause.message))
        }
        exception<AccessDeniedException> { call, cause ->
            call.respond(HttpStatusCode.Forbidden, ErrorResponse(cause.message))
        }
        exception<NumberFormatException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, ErrorResponse("$cause"))
        }
        exception<InvalidLoginException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, ErrorResponse(cause.message))
        }
        exception<UserAlreadyExistsException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, ErrorResponse(cause.message))
        }
        exception<BadRequestException> {call, cause ->
            call.respond(HttpStatusCode.BadRequest, ErrorResponse(cause.cause?.message ?: cause.message))
        }
    }
}

fun Application.configureJWT() {
    authentication {
        jwt("auth-jwt") {
            realm = JWT_REALM
            verifier(
                JWT
                    .require(Algorithm.HMAC256(JWT_SECRET))
                    .withAudience(JWT_AUDIENCE)
                    .withIssuer(JWT_ISSUER)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(JWT_AUDIENCE)
                    && credential.payload.getClaim("login").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }

            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}