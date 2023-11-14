package edu.phystech.kotlin.backend.repository.user.impl

import edu.phystech.kotlin.backend.common.exception.UserAlreadyExistsException
import edu.phystech.kotlin.backend.model.user.User
import edu.phystech.kotlin.backend.repository.user.UserRepository
import edu.phystech.kotlin.backend.repository.user.model.UserEntity
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.transactions.transaction
import org.postgresql.util.PSQLState

class DatabaseUserRepository : UserRepository {
    override fun registerUser(user: User) {
        try {
            transaction {
                UserEntity.new(user.login) {
                    name = user.name
                    registrationTime = user.registrationTime
                    passwordHashed = user.passwordHashed
                }
            }
        } catch (e: ExposedSQLException) {
            if (e.sqlState == PSQLState.UNIQUE_VIOLATION.state) {
                throw UserAlreadyExistsException(user.login)
            }
            throw e
        }
    }

    override fun getUser(login: String): User? {
        return transaction { UserEntity.findById(login)?.toUser() }
    }

    private fun UserEntity.toUser(): User {
        return User(
            login = id.value,
            name = name,
            registrationTime = registrationTime,
            passwordHashed = passwordHashed
        )
    }
}