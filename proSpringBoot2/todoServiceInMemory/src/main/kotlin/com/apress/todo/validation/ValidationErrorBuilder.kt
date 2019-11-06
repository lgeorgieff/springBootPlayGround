package com.apress.todo.validation

import org.springframework.validation.Errors

object ValidationErrorBuilder {
    fun fromBindingErrors(errors: Errors) =
            ToDoValidationError("""Validation failed. ${errors.errorCount} error(s)""")
                .apply {
                    errors.allErrors.forEach {
                        if (it.defaultMessage != null) addValidationError(it.defaultMessage!!)
                    }
                }
}
