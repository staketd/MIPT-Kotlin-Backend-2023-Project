@file:UseSerializers(InstantSerializer::class)

package edu.phystech.kotlin.backend.model.blog

import edu.phystech.kotlin.backend.common.InstantSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant

@Serializable
data class BlogRecord(
    val id: ULong,
    val text: String,
    val updateTime: Instant,
    val owner: String
)