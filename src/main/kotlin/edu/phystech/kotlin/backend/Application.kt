package edu.phystech.kotlin.backend

import edu.phystech.kotlin.backend.api.configureApi
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::blogApi)
        .start(wait = true)
}

fun Application.blogApi() {
    configureDatabase()
    configureServer()
    configureApi()
}