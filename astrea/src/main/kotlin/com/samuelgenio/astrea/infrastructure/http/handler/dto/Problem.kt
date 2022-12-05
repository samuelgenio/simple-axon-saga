package com.samuelgenio.astrea.infrastructure.http.handler.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Problem(
    val status: Int,
    val timestamp: LocalDateTime,
    val type: String? = null,
    val title: String,
    val detail: String? = null,
    var userMessage: String? = null,
    var fields: List<Field>? = null
)

data class Field(val name: String, val userMessage: String?)
