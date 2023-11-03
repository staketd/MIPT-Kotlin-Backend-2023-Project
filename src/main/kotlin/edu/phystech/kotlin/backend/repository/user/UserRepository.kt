package edu.phystech.kotlin.backend.repository.user

interface UserRepository {
    fun registerUser(login: String, name: String, password: String)

    fun validateLoginPassword(login: String, password: String)
}