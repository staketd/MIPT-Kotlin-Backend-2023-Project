package edu.phystech.kotlin.backend.repository.user.model

import edu.phystech.kotlin.backend.service.UserService.Companion.MAX_LOGIN_LENGTH
import edu.phystech.kotlin.backend.service.UserService.Companion.MAX_NAME_LENGTH
import kotlinx.datetime.Instant
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object UsersTable: IdTable<String>("users") {
    val name: Column<String> = varchar("name", length = MAX_NAME_LENGTH)
    val passwordHashed: Column<ByteArray> = binary("password_hashed")
    val registrationTime: Column<Instant> = timestamp("registration_time")

    override val id = varchar("login", length = MAX_LOGIN_LENGTH).entityId()

    override val primaryKey = PrimaryKey(id, name = "PK_users_id")
}