package com.example.reactiveerrorhandling.model

class HelloError(message: String?, cause: Throwable?) : Throwable(message, cause) {
    constructor(message: String) : this(message, null)
    constructor() : this(null, null)
}
