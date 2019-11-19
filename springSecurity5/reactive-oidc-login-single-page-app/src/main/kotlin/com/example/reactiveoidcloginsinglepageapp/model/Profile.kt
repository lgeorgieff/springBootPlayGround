package com.example.reactiveoidcloginsinglepageapp.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed

data class Profile(@Id val id: String = ObjectId.get().toString(), @Indexed val email: String)
