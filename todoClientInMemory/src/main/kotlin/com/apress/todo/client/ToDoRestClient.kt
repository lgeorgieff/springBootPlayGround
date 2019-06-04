package com.apress.todo.client

import com.apress.todo.client.model.ToDo
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.net.URI

@Service
class ToDoRestClient(private val properties: ToDoRestClientProperties) {
    private val restTemplate = RestTemplate().apply { errorHandler = ToDoErrorHandler() }

    fun findAll(): List<ToDo> {
        val requestEntity = RequestEntity<List<ToDo>>(HttpMethod.GET,
                URI(properties.url + properties.basePath))
        val response = restTemplate.exchange(requestEntity,
                object: ParameterizedTypeReference<List<ToDo>>(){})

        return if(response.statusCode == HttpStatus.OK) response.body!! else listOf()
    }

    fun findById(id: String) = restTemplate.getForObject(
            properties.url + properties.basePath + "/{id}", ToDo::class.java, mapOf("id" to id))

    fun upsert(todo: ToDo): ToDo? {
        val requestEntity = RequestEntity(todo, HttpMethod.POST,
                URI(properties.url + properties.basePath))
        val response = restTemplate.exchange(requestEntity,
                object: ParameterizedTypeReference<ToDo>(){})

        return if(response.statusCode == HttpStatus.CREATED)
            restTemplate.getForObject(response.headers.location!!, ToDo::class.java)
        else
            null
    }

    fun setCompleted(id: String): ToDo? {
        restTemplate.postForObject(properties.url + properties.basePath + "/{id}?_method=patch",
                null, ResponseEntity::class.java, mapOf("id" to id))
        return findById(id)
    }

    fun delete(id: String) =
        restTemplate.delete(properties.url + properties.basePath + "/{id}", mapOf("id" to id))
}