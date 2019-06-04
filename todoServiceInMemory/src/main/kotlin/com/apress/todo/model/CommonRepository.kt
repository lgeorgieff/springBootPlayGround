package com.apress.todo.model

interface CommonRepository<T>{
    fun save(model: T): T
    fun save(models: List<T>): List<T>
    fun delete(model: T)
    fun findById(id: String): T
    fun tryFindById(id: String): T?
    fun findAll(): List<T>
}
