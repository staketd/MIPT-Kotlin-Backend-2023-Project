package edu.phystech.kotlin.backend.repository.blog.impl

import edu.phystech.kotlin.backend.model.blog.BlogRecord
import edu.phystech.kotlin.backend.repository.blog.BlogRepository
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class InMemoryBlogRepository: BlogRepository {
    private val records: ConcurrentMap<ULong, BlogRecord> = ConcurrentHashMap()
    private var nextRecordId: ULong = 0u

    override fun getAll(login: String): Collection<BlogRecord> =
        records.values
            .filter { it.owner == login }
            .sortedBy { it.id }

    override fun getPage(login: String, offset: Int, limit: Int): Collection<BlogRecord> =
        records.values
            .filter { it.owner == login }
            .sortedBy { it.id }
            .subList(minOf(offset, records.size), minOf(offset + limit, records.size))

    override fun getById(id: ULong): BlogRecord? = records[id]

    override fun update(record: BlogRecord) {
        records[record.id] = record
    }

    override fun create(record: BlogRecord): BlogRecord {
        val newBlog = record.copy(id = nextRecordId++)
        update(newBlog)
        return newBlog
    }

    override fun deleteById(id: ULong) {
        records.remove(id)
    }
}