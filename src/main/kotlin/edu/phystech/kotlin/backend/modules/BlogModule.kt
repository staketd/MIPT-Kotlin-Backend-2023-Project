package edu.phystech.kotlin.backend.modules

import edu.phystech.kotlin.backend.repository.blog.BlogRepository
import edu.phystech.kotlin.backend.repository.blog.impl.DatabaseBlogRepository
import edu.phystech.kotlin.backend.service.BlogService
import edu.phystech.kotlin.backend.service.impl.BlogServiceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val blogModule = module {
    singleOf(::DatabaseBlogRepository) bind BlogRepository::class

    singleOf(::BlogServiceImpl) bind BlogService::class
}