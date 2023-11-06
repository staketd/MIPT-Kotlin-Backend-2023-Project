package edu.phystech.kotlin.backend.repository.blog.model

import edu.phystech.kotlin.backend.repository.user.model.UsersTable
import edu.phystech.kotlin.backend.service.BlogService
import edu.phystech.kotlin.backend.service.UserService
import kotlinx.datetime.Clock
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object BlogsTable : LongIdTable("blogs") {
    val text = varchar("text", length = BlogService.MAX_TEXT_LENGTH)
    val updateTime = timestamp("update_time").clientDefault { Clock.System.now() }
    val owner = varchar("owner", length = UserService.MAX_LOGIN_LENGTH).references(UsersTable.id)
}