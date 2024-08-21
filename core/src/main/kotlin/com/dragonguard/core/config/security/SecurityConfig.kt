package com.dragonguard.core.config.security

import com.dragonguard.core.config.security.jwt.JwtAuthenticationFilter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.CorsUtils
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.servlet.HandlerExceptionResolver

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val oAuth2UserService: OAuth2UserService<OAuth2UserRequest, OAuth2User>,
    private val authenticationSuccessHandler: AuthenticationSuccessHandler,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    @param:Qualifier("handlerExceptionResolver") private val resolver: HandlerExceptionResolver,
) {
    @Bean
    fun configure(http: HttpSecurity): SecurityFilterChain =
        http
            .cors { it.configurationSource(corsConfigurationSource()) }
            .csrf { it.disable() }
            .httpBasic {
                it.disable()
            }.formLogin {
                it.disable()
            }.authorizeHttpRequests {
                it
                    .requestMatchers(CorsUtils::isPreFlightRequest)
                    .permitAll()
                    .requestMatchers("/oauth2/**", "/auth/**")
                    .permitAll()
                    .requestMatchers("/admin/**")
                    .hasRole("ADMIN")
                    .anyRequest()
                    .authenticated()
            }.oauth2Login {
                it.authorizationEndpoint { endpoint ->
                    endpoint.baseUri("/oauth2/authorize")
                }
                it.redirectionEndpoint { endpoint ->
                    endpoint.baseUri("/oauth2/callback/*")
                }
                it.userInfoEndpoint { endpoint ->
                    endpoint.userService(oAuth2UserService::loadUser)
                }
                it.successHandler(authenticationSuccessHandler)
            }.sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling {
                it.authenticationEntryPoint { request, response, authException ->
                    resolver.resolveException(request, response, null, authException)
                }
                it.accessDeniedHandler { request, response, accessDeniedException ->
                    resolver.resolveException(request, response, null, accessDeniedException)
                }
            }.build()

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.addAllowedOrigin("*")
        configuration.addAllowedHeader("*")
        configuration.addAllowedMethod("*")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}
