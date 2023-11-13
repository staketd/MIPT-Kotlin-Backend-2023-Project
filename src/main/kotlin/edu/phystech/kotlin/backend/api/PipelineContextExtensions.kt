package edu.phystech.kotlin.backend.api

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.util.pipeline.*

fun PipelineContext<Unit, ApplicationCall>.getUserLogin(): String =
    call.principal<JWTPrincipal>()!!.payload.getClaim("login").asString()
fun PipelineContext<Unit, ApplicationCall>.getPathParameter(name: String): String? = call.parameters[name]
fun PipelineContext<Unit, ApplicationCall>.getUrlParameter(name: String): String? = call.request.queryParameters[name]