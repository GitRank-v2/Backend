package com.dragonguard.core.config.security.oauth

import com.dragonguard.core.config.security.jwt.JwtProvider
import com.dragonguard.core.config.security.jwt.JwtToken
import com.dragonguard.core.config.security.oauth.user.UserPrinciple
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder

@Component
class OAuth2AuthenticationSuccessHandler(
    private val jwtProvider: JwtProvider,
    @Value("\${app.oauth2.authorizedRedirectUri}") private val redirectUri: String,
) : SimpleUrlAuthenticationSuccessHandler() {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        authentication: Authentication,
    ) {
        if (response.isCommitted) {
            return
        }
        super.clearAuthenticationAttributes(request)

        val loginUser = authentication.principal as UserPrinciple
        val jwtToken: JwtToken = jwtProvider.createToken(loginUser)
        val targetUri = determineTargetUrl(jwtToken)

        redirectStrategy.sendRedirect(request, response, targetUri)
    }

    private fun determineTargetUrl(jwtToken: JwtToken): String =
        UriComponentsBuilder
            .fromUriString(redirectUri)
            .queryParam(ACCESS_TAG, jwtToken.accessToken)
            .queryParam(REFRESH_TAG, jwtToken.refreshToken)
            .build()
            .toUriString()

    companion object {
        private const val REFRESH_TAG = "refresh"
        private const val ACCESS_TAG = "access"
    }
}
