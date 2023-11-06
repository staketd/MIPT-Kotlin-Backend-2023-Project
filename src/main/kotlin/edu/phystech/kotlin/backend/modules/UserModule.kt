package edu.phystech.kotlin.backend.modules

import edu.phystech.kotlin.backend.repository.user.UserRepository
import edu.phystech.kotlin.backend.repository.user.impl.DatabaseUserRepository
import edu.phystech.kotlin.backend.repository.user.impl.InMemoryUserRepository
import edu.phystech.kotlin.backend.service.UserService
import edu.phystech.kotlin.backend.service.impl.UserServiceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val userModule = module {
    singleOf(::DatabaseUserRepository) bind UserRepository::class
    singleOf(::UserServiceImpl) bind UserService::class
}