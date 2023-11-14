package edu.phystech.kotlin.backend.service

interface UserService {

    fun registerUser(login: String, name: String, password: String)

    fun validateLoginPassword(login: String, password: String)
}