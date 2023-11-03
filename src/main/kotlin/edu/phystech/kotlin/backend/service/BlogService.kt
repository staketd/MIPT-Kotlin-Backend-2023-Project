package edu.phystech.kotlin.backend.service

import edu.phystech.kotlin.backend.common.exception.AccessDeniedException
import edu.phystech.kotlin.backend.common.exception.ValidationException
import edu.phystech.kotlin.backend.model.blog.BlogRecord
import edu.phystech.kotlin.backend.repository.blog.BlogRepository
import java.time.Instant

class BlogService(private val blogRepository: BlogRepository) {
    private val maxTextLength = 500

    fun getAll(login: String): Collection<BlogRecord> = blogRepository.getAll(login)

    fun getPage(login: String, offset: Int, limit: Int): Collection<BlogRecord> =
        blogRepository.getPage(login, offset, limit)

    fun getById(login: String, id: ULong): BlogRecord {
        val blog = blogRepository.getById(id)
            ?: throw AccessDeniedException()

        if (blog.owner != login) throw AccessDeniedException()

        return blog
    }

    fun update(login: String, id: ULong, text: String): BlogRecord {
        validateTextLength(text)
        val oldBlog = getById(login, id)
        val newBlog = oldBlog.copy(text = text, updateTime = Instant.now())
        blogRepository.update(newBlog)
        return newBlog
    }

    fun createBlog(login: String, text: String): BlogRecord {
        validateTextLength(text)
        val blog = BlogRecord(0u, text, Instant.now(), login)

        return blogRepository.create(blog)
    }

    fun deleteBlog(login: String, id: ULong) {
        getById(login, id) // to check access

        blogRepository.deleteById(id)
    }

    private fun validateTextLength(text: String) {
        if (text.length > maxTextLength) {
            throw ValidationException("Blog record text is too long, max length is " +
                    "$maxTextLength, but ${text.length} is received")
        }
    }
}