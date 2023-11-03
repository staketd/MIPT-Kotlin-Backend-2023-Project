package edu.phystech.kotlin.backend.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(val error: String?)
