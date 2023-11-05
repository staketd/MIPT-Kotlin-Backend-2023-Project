package edu.phystech.kotlin.backend.repository.user

import edu.phystech.kotlin.backend.model.user.User

interface UserRepository {
    fun registerUser(user: User)

    fun getUser(login: String): User?
}