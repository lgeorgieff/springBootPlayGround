package com.example.reactivemongodbuserdetailsservice.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AuthenticatedUser(username: String = "", password: String = "", authorities: Collection<out GrantedAuthority>) :
    UserDetails {
    private var username = username
    private var password = password
    private var authorities = authorities

    override fun getAuthorities() = authorities
    override fun isEnabled() = true
    override fun getUsername() = username
    override fun isCredentialsNonExpired() = true
    override fun getPassword() = password
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
}
