package com.example.reactivemongodbuserdetailsservice.jwt

import com.example.reactivemongodbuserdetailsservice.model.AuthenticatedUser
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.Date
import org.springframework.security.core.userdetails.UserDetails

const val ACCESS_TOKEN_VALIDITY_SECONDS = (8 * 60 * 60).toLong()
const val SIGNING_KEY = "top_secret"

fun getExpirationDateFromToken(token: String): Date? = getClaimFromToken(token, Claims::getExpiration)

fun <T> getClaimFromToken(token: String, claimsResolver: (Claims) -> T): T = Jwts
    .parser()
    .setSigningKey(SIGNING_KEY)
    .parseClaimsJws(token)
    .body
    .run { claimsResolver(this) }

fun getUsernameFromToken(token: String): String? = getClaimFromToken(token, Claims::getSubject)

fun generateToken(user: AuthenticatedUser): String {
    val claims = Jwts.claims().setSubject(user.username)
    claims["scopes"] = user.authorities

    return Jwts.builder()
        .setClaims(claims)
        .setIssuer("http://sample.com")
        .setIssuedAt(Date(System.currentTimeMillis()))
        .setExpiration(Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
        .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
        .compact()
}

fun validateToken(token: String, userDetails: UserDetails) = getUsernameFromToken(token) ==
    userDetails.username && (!isTokenExpired(token))

private fun isTokenExpired(token: String) = getExpirationDateFromToken(token)?.before(Date()) ?: true

private fun getAllClaimsFromToken(token: String) = Jwts
    .parser()
    .setSigningKey(SIGNING_KEY)
    .parseClaimsJws(token)
    .body
