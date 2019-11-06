package com.example.todoomvc.domain

import java.time.LocalDateTime
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id

data class Todo(
    @Id val id: String = ObjectId.get().toString(),
    val description: String,
    val created: LocalDateTime = LocalDateTime.now(),
    val modified: LocalDateTime = LocalDateTime.now(),
    val completed: Boolean = false
)
