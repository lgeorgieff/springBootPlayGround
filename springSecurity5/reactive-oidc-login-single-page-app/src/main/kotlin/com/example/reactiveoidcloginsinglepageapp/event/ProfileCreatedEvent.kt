package com.example.reactiveoidcloginsinglepageapp.event

import com.example.reactiveoidcloginsinglepageapp.model.Profile
import org.springframework.context.ApplicationEvent

class ProfileCreatedEvent(val source: Profile) : ApplicationEvent(source)
