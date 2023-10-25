@file:UseSerializers(InstantSerializer::class)

package edu.phystech.kotlin.backend.blog.model

import edu.phystech.kotlin.backend.util.InstantSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant

@Serializable
data class BlogRecord(val id: ULong, val text: String, val updateTime: Instant) {
}