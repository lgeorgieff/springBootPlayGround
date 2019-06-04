package com.apress.todo.model

import java.time.LocalDateTime
import java.util.*

class ToDo private constructor (id: String?, description: String, created: LocalDateTime?,
                                modified: LocalDateTime?, completed: Boolean) {
    val id: String = id ?: UUID.randomUUID().toString()
    val description: String = description
    val created: LocalDateTime
    val modified: LocalDateTime
    val completed: Boolean = completed

    init {
        if(description.isNullOrBlank()) {
            throw IllegalArgumentException(
                    """description must not be null or blank, but is "$description"""")
        }
        if(id.isNullOrBlank()) {
            throw IllegalArgumentException("""id must not be null or blank, but is "$id"""")
        }

        val localDateTime = LocalDateTime.now()
        this.created = created ?: localDateTime
        this.modified = modified ?: localDateTime
    }

    constructor(description: String, id: String? = null)
            :this(id, description, null, null, false)

    constructor(todo: ToDo, completed: Boolean)
            :this(todo.id, todo.description, todo.created, todo.modified, completed)

    override fun toString() = """
        |{
        |   "description": "$description",
        |   "completed": $completed,
        |   "id": "$id",
        |   "created": "$created",
        |   "modified": "$modified"
        |}""".trimMargin()
}