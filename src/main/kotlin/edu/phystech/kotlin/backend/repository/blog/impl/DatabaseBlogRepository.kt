package edu.phystech.kotlin.backend.repository.blog.impl

import edu.phystech.kotlin.backend.model.blog.BlogRecord
import edu.phystech.kotlin.backend.repository.blog.BlogRepository
import edu.phystech.kotlin.backend.repository.blog.model.BlogRecordEntity
import edu.phystech.kotlin.backend.repository.blog.model.BlogsTable
import edu.phystech.kotlin.backend.repository.user.model.UserEntity
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class DatabaseBlogRepository : BlogRepository {
    override fun getAll(login: String): Collection<BlogRecord> {
        return transaction {
            val query = BlogsTable.select(BlogsTable.owner.eq(login)).orderBy(BlogsTable.id)
            val entities = BlogRecordEntity.wrapRows(query)
            entities.map { it.toBlogRecord() }
        }
    }

    override fun getPage(login: String, offset: Int, limit: Int): Collection<BlogRecord> {
        return transaction {
            val query = BlogsTable.select(BlogsTable.owner.eq(login))
                .orderBy(BlogsTable.id)
                .limit(limit, offset = offset.toLong())
            val entities = BlogRecordEntity.wrapRows(query)
            entities.map { it.toBlogRecord() }
        }
    }

    override fun getById(id: ULong): BlogRecord? {
        return transaction { BlogRecordEntity.findById(id.toLong())?.toBlogRecord() }
    }

    override fun update(record: BlogRecord) {
        transaction {
            BlogsTable.update({ BlogsTable.id eq record.id.toLong() }) {
                it[text] = record.text
            }
        }
    }

    override fun create(record: BlogRecord): BlogRecord {
        return transaction {
            BlogRecordEntity.new {
                text = record.text
                owner = UserEntity[record.owner]
            }.toBlogRecord()
        }
    }

    override fun deleteById(id: ULong) {
        return transaction { BlogsTable.deleteWhere { BlogsTable.id.eq(id.toLong()) } }
    }

    private fun BlogRecordEntity.toBlogRecord(): BlogRecord {
        return BlogRecord(
            id = id.value.toULong(),
            text = text,
            updateTime = updateTime,
            owner = owner.id.value
        )
    }
}