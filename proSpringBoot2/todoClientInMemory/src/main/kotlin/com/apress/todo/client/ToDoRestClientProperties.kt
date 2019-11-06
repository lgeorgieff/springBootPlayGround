package com.apress.todo.client

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "todo")
data class ToDoRestClientProperties(var url: String = "", var basePath: String = "")
