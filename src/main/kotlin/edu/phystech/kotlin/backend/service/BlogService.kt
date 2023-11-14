package edu.phystech.kotlin.backend.service

import edu.phystech.kotlin.backend.model.blog.BlogRecord

interface BlogService {
    companion object {
        const val MAX_TEXT_LENGTH = 500
    }

    fun getAll(login: String): Collection<BlogRecord>

    fun getPage(login: String, offset: Int, limit: Int): Collection<BlogRecord>

    fun getById(login: String, id: ULong): BlogRecord

    fun update(login: String, id: ULong, text: String): BlogRecord

    fun createBlog(login: String, text: String): BlogRecord

    fun deleteBlog(login: String, id: ULong)
}