package edu.phystech.kotlin.backend.api

import edu.phystech.kotlin.backend.api.blog.blogRoute
import edu.phystech.kotlin.backend.api.user.userRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureApi() {

    routing {
        userRoute()
        blogRoute()
    }
}

