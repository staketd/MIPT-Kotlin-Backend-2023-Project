package edu.phystech.kotlin.backend.modules

import edu.phystech.kotlin.backend.repository.user.UserRepository
import edu.phystech.kotlin.backend.repository.user.impl.UserRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val userModule = module {
    singleOf(::UserRepositoryImpl) bind UserRepository::class
}