package edu.phystech.kotlin.backend.repository.user.impl

import edu.phystech.kotlin.backend.model.user.User
import edu.phystech.kotlin.backend.repository.user.UserRepository
import edu.phystech.kotlin.backend.repository.user.model.UserEntity
import edu.phystech.kotlin.backend.repository.user.model.UsersTable
import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseUserRepository : UserRepository {
    override fun registerUser(user: User) {
        transaction {
            UserEntity.new(user.login) {
                name = user.name
                registrationTime = user.registrationTime
                passwordHashed = user.passwordHashed
            }
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