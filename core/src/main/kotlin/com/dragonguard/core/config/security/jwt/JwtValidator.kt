package com.dragonguard.core.config.security.jwt

import com.dragonguard.core.config.security.oauth.user.UserPrincipleMapper
import com.dragonguard.core.domain.member.MemberRepository
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key

@Component
class JwtValidator(
    private val key: Key,
    private val memberRepository: MemberRepository,
    private val userPrincipleMapper: UserPrincipleMapper,
) {
    fun getAuthentication(accessToken: String): Authentication {
        val claims = getTokenBodyClaims(accessToken)
        val loginUser =
            memberRepository.findByIdOrNull(extractIdentifier(claims))?.let {
                userPrincipleMapper.mapToLoginUser(it)
            }
        return UsernamePasswordAuthenticationToken(loginUser, CREDENTIAL, loginUser?.authorities)
    }

    fun extractIdentifier(claims: Claims): Long = claims[ID, String::class.java].toLong()

    fun getTokenBodyClaims(accessToken: String?): Claims =
        Jwts
            .parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(accessToken)
            .body

    companion object {
        private const val CREDENTIAL = ""
        private const val ID = "id"
    }
}
