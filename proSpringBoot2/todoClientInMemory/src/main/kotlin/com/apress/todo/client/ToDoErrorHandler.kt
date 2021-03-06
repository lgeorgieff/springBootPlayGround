package com.apress.todo.client

import java.nio.charset.Charset
import org.slf4j.LoggerFactory
import org.springframework.http.client.ClientHttpResponse
import org.springframework.util.StreamUtils
import org.springframework.web.client.DefaultResponseErrorHandler

class ToDoErrorHandler : DefaultResponseErrorHandler() {
    private val logger = LoggerFactory.getLogger(ToDoClientApplication::class.java)

    override fun handleError(response: ClientHttpResponse) {
        super.handleError(response)
        logger.error(response.statusCode.toString())
        logger.error(StreamUtils.copyToString(response.body, Charset.defaultCharset()))
    }
}
