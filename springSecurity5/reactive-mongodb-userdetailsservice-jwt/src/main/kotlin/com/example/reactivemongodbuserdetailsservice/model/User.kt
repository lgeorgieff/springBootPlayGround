package com.example.reactivemongodbuserdetailsservice.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id

data class User(
    @Id val _id: ObjectId = ObjectId.get(),
    val email: String = "",
    val password: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val authorities: List<String> = listOf()
)
