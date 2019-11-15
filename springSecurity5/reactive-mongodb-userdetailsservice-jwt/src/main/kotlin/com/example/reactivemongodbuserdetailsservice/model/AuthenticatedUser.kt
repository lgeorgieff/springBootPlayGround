package com.example.reactivemongodbuserdetailsservice.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails

class AuthenticatedUser(
    username: String,
    password: String,
    authorities: Collection<GrantedAuthority> = listOf(),
    val firstName: String = "",
    val lastName: String = ""
) : UserDetails {
    constructor(user: User) : this(
        username = user.email,
        password = user.password,
        authorities = AuthorityUtils.createAuthorityList(*user.authorities.toTypedArray()),
        firstName = user.firstName,
        lastName = user.lastName
    )
    private val mAuthorities = authorities
    private val mUsername = username
    private val mPassword = password

    override fun getAuthorities() = mAuthorities
    override fun isEnabled() = true
    override fun getUsername() = mUsername
    override fun isCredentialsNonExpired() = true
    override fun getPassword() = mPassword
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
}
