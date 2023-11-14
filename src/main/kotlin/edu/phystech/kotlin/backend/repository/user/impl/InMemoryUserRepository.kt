package edu.phystech.kotlin.backend.repository.user.impl

import edu.phystech.kotlin.backend.common.exception.UserAlreadyExistsException
import edu.phystech.kotlin.backend.model.user.User
import edu.phystech.kotlin.backend.repository.user.UserRepository
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class InMemoryUserRepository: UserRepository {
    private val users: ConcurrentMap<String, User> = ConcurrentHashMap()

    override fun registerUser(user: User) {
        if (users.containsKey(user.login)) {
            throw UserAlreadyExistsException(user.login)
        }

        users[user.login] = user
    }

    override fun getUser(login: String) = users[login]
}
