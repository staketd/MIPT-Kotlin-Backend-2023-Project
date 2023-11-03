package edu.phystech.kotlin.backend.api

import edu.phystech.kotlin.backend.api.model.BlogModifyRequest
import edu.phystech.kotlin.backend.blog.repository.BlogRepository
import edu.phystech.kotlin.backend.util.BlogNotFoundException
import edu.phystech.kotlin.backend.util.ValidationException
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import org.koin.ktor.ext.inject

fun PipelineContext<Unit, ApplicationCall>.getPathParameter(name: String): String? = call.parameters[name]
fun PipelineContext<Unit, ApplicationCall>.getUrlParameter(name: String): String? = call.request.queryParameters[name]

fun Application.configureApi() {
    val maxTextLength = 500

    routing {
        val blogRepository by inject<BlogRepository>()

        get("/blog/{id}") {
            val blogId = getPathParameter("id")?.toULong() ?: throw ValidationException("Id parameter not specified or invalid")
            val blogRecord = blogRepository.getById(blogId) ?: throw BlogNotFoundException(blogId)
            call.respond(blogRecord)
        }

        put("/blog") {
            val blogModifyRequest = call.receive<BlogModifyRequest>()

            if (blogModifyRequest.blogRecordText.length > 500) {
                throw ValidationException("Blog record text is too long, max length is " +
                        "$maxTextLength, but ${blogModifyRequest.blogRecordText.length} is received")
            }

            call.respond(blogRepository.create(blogModifyRequest.blogRecordText))
        }

        get("/blogs") {
            call.respond(blogRepository.getAll())
        }

        get("/blogs/page") {
            val offset = getUrlParameter("offset")?.toInt() ?:
                throw ValidationException("Offset parameter is not specified of invalid")


            val limit = getUrlParameter("offset")?.toInt() ?:
                throw ValidationException("Limit parameter is not specified of invalid")

            call.respond(blogRepository.getPage(offset, limit))
        }

        patch("/blog/{id}") {
            val blogId = call.parameters["id"]?.toULong() ?: throw ValidationException("Id parameter not specified or invalid")

            val blogModifyRequest = call.receive<BlogModifyRequest>()

            if (blogModifyRequest.blogRecordText.length > 500) {
                throw ValidationException("Blog record text is too long, max length is " +
                        "$maxTextLength, but ${blogModifyRequest.blogRecordText.length} is received")
            }

            call.respond(blogRepository.edit(blogId, blogModifyRequest.blogRecordText))
        }

        delete("/blog/{id}") {
            val blogId = call.parameters["id"]?.toULong() ?: throw ValidationException("Id parameter not specified or invalid")

            call.respondNullable(blogRepository.deleteById(blogId))
        }
    }
}

