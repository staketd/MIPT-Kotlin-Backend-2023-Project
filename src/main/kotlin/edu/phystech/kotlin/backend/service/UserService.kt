package edu.phystech.kotlin.backend.service

import com.auth0.jwt.algorithms.Algorithm
import edu.phystech.kotlin.backend.common.exception.InvalidLoginException
import edu.phystech.kotlin.backend.common.exception.ValidationException
import edu.phystech.kotlin.backend.model.user.User
import edu.phystech.kotlin.backend.repository.user.UserRepository
import kotlinx.datetime.Clock

class UserService(private val userRepository: UserRepository) {
    companion object {
        const val MAX_LOGIN_LENGTH = 100
        const val MAX_NAME_LENGTH = 100
    }
    private val passwordHasher = Algorithm.HMAC256("very_secret_secret")

    fun registerUser(login: String, name: String, password: String) {
        val newUser = User(login, encodePassword(password), name, Clock.System.now())
        validateUser(newUser)
        userRepository.registerUser(newUser)
    }

    fun validateLoginPassword(login: String, password: String) {
        val user = userRepository.getUser(login)
            ?: throw InvalidLoginException()

        if (!user.passwordHashed.contentEquals(encodePassword(password))) throw InvalidLoginException()
    }

    private fun encodePassword(password: String): ByteArray {
        return passwordHasher.sign(password.encodeToByteArray())
    }

    private fun validateUser(user: User) {
        if (user.login.length > MAX_LOGIN_LENGTH) {
            throw ValidationException("Login is too long: max length is $MAX_LOGIN_LENGTH")
        }

        if (user.name.length > MAX_NAME_LENGTH) {
            throw ValidationException("Login is too long: max length is $MAX_NAME_LENGTH")
        }
    }
}