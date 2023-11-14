package edu.phystech.kotlin.backend

import edu.phystech.kotlin.backend.common.config
import edu.phystech.kotlin.backend.repository.blog.model.BlogsTable
import edu.phystech.kotlin.backend.repository.user.model.UsersTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun configureDatabase() {
    val url = config.property("db.endpoint").getString()
    val username = config.property("db.user").getString()
    val password = config.property("db.password").getString()
    val database = Database.connect(url = url, user = username, password = password)
    database.initSchema()
}

private fun Database.initSchema() {
    transaction(this) {
        SchemaUtils.create(UsersTable)
        SchemaUtils.create(BlogsTable)
    }
}