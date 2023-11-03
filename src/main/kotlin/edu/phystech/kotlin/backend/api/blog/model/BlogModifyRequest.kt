package edu.phystech.kotlin.backend.api.blog.model

import kotlinx.serialization.Serializable

@Serializable
data class BlogModifyRequest(val blogRecordText: String)