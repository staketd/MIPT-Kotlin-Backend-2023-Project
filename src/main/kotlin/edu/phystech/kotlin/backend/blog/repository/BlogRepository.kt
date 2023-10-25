package edu.phystech.kotlin.backend.blog.repository

import edu.phystech.kotlin.backend.blog.model.BlogRecord

interface BlogRepository {
    fun getAll(): Collection<BlogRecord>

    fun getPage(offset: Int, limit: Int): Collection<BlogRecord>

    fun getById(id: ULong): BlogRecord?

    fun edit(id: ULong, blogRecordText: String): BlogRecord

    fun create(blogRecordText: String): BlogRecord

    fun deleteById(id: ULong): BlogRecord?
}