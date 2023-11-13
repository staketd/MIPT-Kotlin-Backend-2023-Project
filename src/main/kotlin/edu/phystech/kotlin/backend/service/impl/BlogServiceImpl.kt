package edu.phystech.kotlin.backend.service.impl

import edu.phystech.kotlin.backend.common.exception.AccessDeniedException
import edu.phystech.kotlin.backend.common.exception.ValidationException
import edu.phystech.kotlin.backend.model.blog.BlogRecord
import edu.phystech.kotlin.backend.repository.blog.BlogRepository
import edu.phystech.kotlin.backend.service.BlogService
import kotlinx.datetime.Clock

class BlogServiceImpl(private val blogRepository: BlogRepository): BlogService {
    companion object {
        const val MAX_TEXT_LENGTH = 500
    }

    override fun getAll(login: String): Collection<BlogRecord> = blogRepository.getAll(login)

    override fun getPage(login: String, offset: Int, limit: Int): Collection<BlogRecord> =
        blogRepository.getPage(login, offset, limit)

    override fun getById(login: String, id: ULong): BlogRecord {
        val blog = blogRepository.getById(id)
            ?: throw AccessDeniedException()

        if (blog.owner != login) throw AccessDeniedException()

        return blog
    }

    override fun update(login: String, id: ULong, text: String): BlogRecord {
        validateTextLength(text)
        val oldBlog = getById(login, id)
        val newBlog = oldBlog.copy(text = text, updateTime = Clock.System.now())
        blogRepository.update(newBlog)
        return newBlog
    }

    override fun createBlog(login: String, text: String): BlogRecord {
        validateTextLength(text)
        val blog = BlogRecord(0u, text, Clock.System.now(), login)

        return blogRepository.create(blog)
    }

    override fun deleteBlog(login: String, id: ULong) {
        getById(login, id) // to check access

        blogRepository.deleteById(id)
    }

    private fun validateTextLength(text: String) {
        if (text.length > MAX_TEXT_LENGTH) {
            throw ValidationException("Blog record text is too long, max length is " +
                    "$MAX_TEXT_LENGTH, but ${text.length} is received")
        }
    }
}