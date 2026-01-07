package com.study.kopringchat.global.auth.jwt

import com.study.kopringchat.global.auth.exception.JwtErrorCode
import com.study.kopringchat.global.exception.CustomException
import com.study.kopringchat.member.domain.Member
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.Date
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Component
class JwtUtils(
    private val jwtProperties: JwtProperties,
    private val userDetailsService: UserDetailsService,
) {
    companion object {
        const val TOKEN_TYPE_ACCESS = "access"
        const val TOKEN_TYPE_REFRESH = "refresh"
    }

    private val secretKey: SecretKey = SecretKeySpec(
        jwtProperties.secretKey.toByteArray(StandardCharsets.UTF_8),
        Jwts.SIG.HS256.key().build().algorithm
    )

    fun generate(member: Member): JwtInfo {
        val accessToken = createToken(member, jwtProperties.accessExpired, TOKEN_TYPE_ACCESS)
        val refreshToken = createToken(member, jwtProperties.refreshExpired, TOKEN_TYPE_REFRESH)
        return JwtInfo("Bearer $accessToken", "Bearer $refreshToken")
    }

    private fun createToken(member: Member, tokenExpired: Long, tokenType: String): String {
        val now = Date().time
        return Jwts.builder()
            .claim("id", member.id)
            .claim("email", member.email)
            .claim("role", member.role.name)
            .claim("type", tokenType)
            .issuedAt(Date(now))
            .expiration(Date(now + tokenExpired))
            .signWith(secretKey)
            .compact()
    }

    fun checkTokenInfo(token: String) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token)
        } catch (e: ExpiredJwtException) {
            throw CustomException(JwtErrorCode.JWT_EXPIRED)
        } catch (e: SignatureException) {
            throw CustomException(JwtErrorCode.JWT_INVALID_SIGNATURE)
        } catch (e: MalformedJwtException) {
            throw CustomException(JwtErrorCode.JWT_MALFORMED)
        } catch (e: UnsupportedJwtException) {
            throw CustomException(JwtErrorCode.JWT_UNSUPPORTED)
        } catch (e: Exception) {
            throw CustomException(JwtErrorCode.JWT_UNKNOWN_ERROR)
        }
    }

    fun getToken(token: String): String = token.removePrefix("Bearer ")

    fun getUsername(token: String): String =
        Jwts.parser().verifyWith(secretKey).build()
            .parseSignedClaims(token).payload.get("email", String::class.java)

    fun isRefreshToken(token: String): Boolean {
        val type = Jwts.parser().verifyWith(secretKey).build()
            .parseSignedClaims(token).payload.get("type", String::class.java)
        return type == TOKEN_TYPE_REFRESH
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails = userDetailsService.loadUserByUsername(getUsername(getToken(token)))
        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }

    fun getExpiration(token: String): Long =
        Jwts.parser().verifyWith(secretKey).build()
            .parseSignedClaims(token).payload.expiration.time

    fun refreshAccessToken(member: Member): String =
        "Bearer " + createToken(member, jwtProperties.accessExpired, TOKEN_TYPE_ACCESS)
}
