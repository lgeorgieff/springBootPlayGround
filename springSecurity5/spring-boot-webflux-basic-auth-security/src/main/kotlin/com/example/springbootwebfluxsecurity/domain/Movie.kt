package com.example.springbootwebfluxsecurity.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Movie(@Id val id: Long, val title: String, val genre: String)
