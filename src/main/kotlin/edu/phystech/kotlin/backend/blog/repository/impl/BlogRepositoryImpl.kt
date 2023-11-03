package edu.phystech.kotlin.backend.blog.repository.impl

import edu.phystech.kotlin.backend.blog.model.BlogRecord
import edu.phystech.kotlin.backend.blog.repository.BlogRepository
import edu.phystech.kotlin.backend.util.BlogNotFoundException
import java.time.Instant

class BlogRepositoryImpl: BlogRepository {
    private val records: MutableMap<ULong, BlogRecord> = mutableMapOf()
    private var nextRecordId: ULong = 0u

    override fun getAll(): Collection<BlogRecord> = records.values.sortedBy { it.id }

    override fun getPage(offset: Int, limit: Int): Collection<BlogRecord> =
        records.values.sortedBy { it.id }
            .subList(minOf(offset, records.size), minOf(offset + limit, records.size))

    override fun getById(id: ULong): BlogRecord? = records[id]

    override fun edit(id: ULong, blogRecordText: String): BlogRecord {
        records[id] ?: throw BlogNotFoundException(id)

        records[id] = BlogRecord(id, blogRecordText, Instant.now())
        return records.getValue(id)
    }

    override fun create(blogRecordText: String): BlogRecord {
        records[nextRecordId] = BlogRecord(nextRecordId, blogRecordText, Instant.now())
        return records.getValue(nextRecordId++)
    }

    override fun deleteById(id: ULong): BlogRecord? = records.remove(id)
}