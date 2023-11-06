package edu.phystech.kotlin.backend

import edu.phystech.kotlin.backend.repository.blog.model.BlogsTable
import edu.phystech.kotlin.backend.repository.user.model.UsersTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun configureDatabase() {
    val url = "jdbc:postgresql://localhost:9000/practice_db"
    val username = "mipt_kotlin_backend"
    val password = "password"
    val database = Database.connect(url = url, user = username, password = password)
    database.initSchema()
}

private fun Database.initSchema() {
    transaction(this) {
        SchemaUtils.create(UsersTable)
        SchemaUtils.create(BlogsTable)
    }
}