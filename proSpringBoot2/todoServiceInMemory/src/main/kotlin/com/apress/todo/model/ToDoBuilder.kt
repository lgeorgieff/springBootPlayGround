package com.apress.todo.model

object ToDoBuilder {
    private var description: String = ""
    private var id: String? = null

    fun withDescription(description: String) = apply { this.description = description }
    fun withId(id: String) = apply { this.id = id }
    fun build() = ToDo(description = description, id = id)
}
