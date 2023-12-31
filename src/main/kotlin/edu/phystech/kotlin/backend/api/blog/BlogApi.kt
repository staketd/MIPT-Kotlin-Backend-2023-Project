package edu.phystech.kotlin.backend.api.blog

import edu.phystech.kotlin.backend.api.blog.model.BlogModifyRequest
import edu.phystech.kotlin.backend.api.getPathParameter
import edu.phystech.kotlin.backend.api.getUrlParameter
import edu.phystech.kotlin.backend.api.getUserLogin
import edu.phystech.kotlin.backend.common.exception.ValidationException
import edu.phystech.kotlin.backend.service.BlogService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.blogRoute() {

    val blogService by inject<BlogService>()

    authenticate("auth-jwt") {
        get("/blog/{id}") {
            val blogId = getPathParameter("id")?.toULong()
                ?: throw ValidationException("Id parameter not specified or invalid")

            val blogRecord = blogService.getById(getUserLogin(), blogId)

            call.respond(blogRecord)
        }

        put("/blog") {
            val blogModifyRequest = call.receive<BlogModifyRequest>()

            call.respond(blogService.createBlog(getUserLogin(), blogModifyRequest.blogRecordText))
        }

        get("/blogs") {
            call.respond(blogService.getAll(getUserLogin()))
        }

        get("/blogs/page") {
            val offset = getUrlParameter("offset")?.toInt()
                ?: throw ValidationException("Offset parameter is not specified of invalid")

            val limit = getUrlParameter("offset")?.toInt()
                ?: throw ValidationException("Limit parameter is not specified of invalid")

            call.respond(blogService.getPage(getUserLogin(), offset, limit))
        }

        patch("/blog/{id}") {
            val blogId = call.parameters["id"]?.toULong()
                ?: throw ValidationException("Id parameter not specified or invalid")

            val blogModifyRequest = call.receive<BlogModifyRequest>()

            call.respond(blogService.update(getUserLogin(), blogId, blogModifyRequest.blogRecordText))
        }

        delete("/blog/{id}") {
            val blogId = getPathParameter("id")?.toULong()
                ?: throw ValidationException("Id parameter not specified or invalid")

            call.respondNullable(blogService.deleteBlog(getUserLogin(), blogId))
        }
    }
}