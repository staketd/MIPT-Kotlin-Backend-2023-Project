package edu.phystech.kotlin.backend

import edu.phystech.kotlin.backend.blog.repository.BlogRepository
import edu.phystech.kotlin.backend.blog.repository.impl.BlogRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val blogModule = module {
    singleOf(::BlogRepositoryImpl) bind BlogRepository::class
}