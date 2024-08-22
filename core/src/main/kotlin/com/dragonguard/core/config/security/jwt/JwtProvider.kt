package com.dragonguard.core.config.security.jwt

import com.dragonguard.core.config.security.oauth.user.UserPrinciple
import com.dragonguard.core.domain.member.MemberRepository
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.stereotype.Component
import java.security.Key
import java.util.Date

@Component
class JwtProvider(
    private val memberRepository: MemberRepository,
    private val key: Key,
) {
    fun createToken(userDetails: UserPrinciple): JwtToken {
        val accessToken =
            getToken(userDetails, getClaims(userDetails), ACCESS_TOKEN_EXPIRE_LENGTH)
        val refreshToken =
            getToken(userDetails, getClaims(), REFRESH_TOKEN_EXPIRE_LENGTH)

        saveRefreshToken(refreshToken, userDetails)

        return JwtToken(accessToken, refreshToken)
    }

    fun validateToken(token: String?): Boolean {
        try {
            Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
            return true
        } catch (e: ExpiredJwtException) {
            return false
        } catch (e: UnsupportedJwtException) {
            return false
        } catch (e: IllegalStateException) {
            return false
        }
    }

    private fun saveRefreshToken(
        refreshToken: String,
        userDetails: UserPrinciple,
    ) {
        val id = userDetails.name.toLong()

        memberRepository.updateRefreshToken(id, refreshToken)
    }

    private fun getClaims(userDetails: UserPrinciple): Claims {
        val claims = Jwts.claims()
        claims[USER_ID_CLAIM_NAME] = userDetails.name
        return claims
    }

    private fun getClaims(): Claims = Jwts.claims()

    private fun getToken(
        loginUser: UserPrinciple,
        claims: Claims,
        validationSecond: Long,
    ): String {
        val now = Date().time

        return Jwts
            .builder()
            .setSubject(loginUser.name)
            .setClaims(claims)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(Date(now + validationSecond))
            .compact()
    }

    companion object {
        private const val ACCESS_TOKEN_EXPIRE_LENGTH = 60L * 60 * 24 * 1000 // 1 Day
        private const val REFRESH_TOKEN_EXPIRE_LENGTH = 60L * 60 * 24 * 14 * 1000 // 14 Days
        private const val USER_ID_CLAIM_NAME = "id"
    }
}
