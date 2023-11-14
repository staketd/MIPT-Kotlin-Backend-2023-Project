package edu.phystech.kotlin.backend.common.exception

class UserAlreadyExistsException(login: String) : RuntimeException("User with login $login already exists")