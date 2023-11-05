package edu.phystech.kotlin.backend.model.blog

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class BlogRecord(
    val id: ULong,
    val text: String,
    val updateTime: Instant,
    val owner: String
)