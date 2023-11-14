package edu.phystech.kotlin.backend.repository.blog

import edu.phystech.kotlin.backend.model.blog.BlogRecord

interface BlogRepository {
    fun getAll(login: String): Collection<BlogRecord>

    fun getPage(login: String, offset: Int, limit: Int): Collection<BlogRecord>

    fun getById(id: ULong): BlogRecord?

    fun update(record: BlogRecord)

    fun create(record: BlogRecord): BlogRecord

    fun deleteById(id: ULong)
}