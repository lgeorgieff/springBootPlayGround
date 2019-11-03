package com.example.springbootwebfluxsecurity.domain

import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface MovieRepository : ReactiveCrudRepository<Movie, Long>
