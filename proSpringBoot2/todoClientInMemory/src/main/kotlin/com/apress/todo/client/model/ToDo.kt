package com.apress.todo.client.model

import java.time.LocalDateTime
import java.util.*

data class ToDo (
        val id: String,
        val description: String,
        val created: LocalDateTime,
        val modified: LocalDateTime,
        val completed: Boolean) {

    companion object {
        fun create(description: String): ToDo {
            val localDateTime = LocalDateTime.now()
            return ToDo(
                    id = UUID.randomUUID().toString(),
                    description = description,
                    created = localDateTime,
                    modified = localDateTime,
                    completed = false
            )
        }
    }
}