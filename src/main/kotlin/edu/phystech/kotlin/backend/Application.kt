package edu.phystech.kotlin.backend

import edu.phystech.kotlin.backend.api.configureApi
import edu.phystech.kotlin.backend.api.model.ErrorResponse
import edu.phystech.kotlin.backend.util.BlogNotFoundException
import edu.phystech.kotlin.backend.util.ValidationException
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import kotlinx.serialization.json.Json
import org.koin.ktor.plugin.Koin

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::blogApi).start(wait = true)
}


fun Application.configureServer() {
    install(Koin) {
        modules(blogModule)
    }
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, ErrorResponse("500: $cause"))
        }
        exception<ValidationException> {call, cause ->
            call.respond(HttpStatusCode.BadRequest, ErrorResponse(cause.message))
        }
        exception<BlogNotFoundException> {call, cause ->
            call.respond(HttpStatusCode.NotFound, ErrorResponse(cause.message))
        }
        exception<NumberFormatException> {call, cause ->
            call.respond(HttpStatusCode.BadRequest, ErrorResponse("$cause"))
        }
    }
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
        })
    }
}

fun Application.blogApi() {
    configureServer()
    configureApi()
}