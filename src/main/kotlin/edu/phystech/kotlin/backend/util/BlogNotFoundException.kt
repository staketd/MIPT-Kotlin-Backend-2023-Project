package edu.phystech.kotlin.backend.util

class BlogNotFoundException(blogId: ULong) : RuntimeException("Blog with id $blogId not found") {
}