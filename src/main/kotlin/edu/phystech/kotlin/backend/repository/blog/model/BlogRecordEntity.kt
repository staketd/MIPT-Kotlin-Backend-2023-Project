package edu.phystech.kotlin.backend.repository.blog.model

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class BlogRecordEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object: LongEntityClass<BlogRecordEntity>(BlogsTable)

    var text by BlogsTable.text
    var updateTime by BlogsTable.updateTime
    var owner by BlogsTable.owner
}