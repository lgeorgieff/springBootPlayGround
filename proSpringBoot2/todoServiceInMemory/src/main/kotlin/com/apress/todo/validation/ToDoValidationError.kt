package com.apress.todo.validation

import com.fasterxml.jackson.annotation.JsonInclude

class ToDoValidationError(val errorMessage: String) {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private val mErrors = mutableListOf<String>()

    fun addValidationError(error: String) {
        mErrors.add(error)
    }

    val errors: List<String>
        get() = mErrors
}
