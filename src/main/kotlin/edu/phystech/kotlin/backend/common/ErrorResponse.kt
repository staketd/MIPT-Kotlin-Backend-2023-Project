package edu.phystech.kotlin.backend.common

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(val error: String?)
