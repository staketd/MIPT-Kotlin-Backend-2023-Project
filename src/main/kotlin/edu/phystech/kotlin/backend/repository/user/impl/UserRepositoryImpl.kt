package edu.phystech.kotlin.backend.repository.user.impl

import com.auth0.jwt.algorithms.Algorithm
import edu.phystech.kotlin.backend.common.exception.InvalidLoginException
import edu.phystech.kotlin.backend.common.exception.UserAlreadyExistsException
import edu.phystech.kotlin.backend.model.user.User
import edu.phystech.kotlin.backend.repository.user.UserRepository
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class UserRepositoryImpl: UserRepository {
    private val users: ConcurrentMap<String, User> = ConcurrentHashMap()
    private val passwordHasher = Algorithm.HMAC256("very_secret_secret")

    override fun registerUser(login: String, name: String, password: String) {
        if (users.containsKey(login)) {
            throw UserAlreadyExistsException(login)
        }

        users[login] = User(login, encodePassword(password), name, Instant.now())
    }

    override fun validateLoginPassword(login: String, password: String) {
        val user = users[login] ?: throw InvalidLoginException()

        if (!user.passwordHashed.contentEquals(encodePassword(password))) throw InvalidLoginException()
    }

    private fun encodePassword(password: String): ByteArray {
        return passwordHasher.sign(password.encodeToByteArray())
    }

}
