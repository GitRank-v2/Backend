package com.dragonguard.core.config.security.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtValidator: JwtValidator,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        parseBearerToken(request)?.let {
            SecurityContextHolder.getContext().authentication = jwtValidator.getAuthentication(it)
        }

        filterChain.doFilter(request, response)
    }

    private fun parseBearerToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(TOKEN_TAG)
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            bearerToken.substring(TOKEN_PREFIX_LENGTH)
        } else {
            null
        }
    }

    companion object {
        private const val TOKEN_TAG: String = "Authorization"
        private const val TOKEN_PREFIX: String = "Bearer "
        private const val TOKEN_PREFIX_LENGTH: Int = TOKEN_PREFIX.length
    }
}
