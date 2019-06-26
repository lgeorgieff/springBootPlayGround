package com.example.todooauth.domain

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import java.time.LocalDateTime

data class Todo(
        @Id val id: String = ObjectId.get().toString(),
        val description: String,
        val created: LocalDateTime = LocalDateTime.now(),
        val modified: LocalDateTime = LocalDateTime.now(),
        val completed: Boolean = false
)