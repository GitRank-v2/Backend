package com.dragonguard.core.config.security.oauth.user

import com.dragonguard.core.domain.member.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

class UserPrinciple(
    private val member: Member,
    private val authorities: Collection<GrantedAuthority?>,
    private val attributes: Map<String, Any>,
) : UserDetails,
    OAuth2User {
    override fun getName(): String = member.getId().toString()

    override fun getAttributes(): Map<String, Any> = attributes

    override fun getAuthorities(): Collection<GrantedAuthority?> = authorities

    override fun getPassword(): String = DEFAULT_PASSWORD

    override fun getUsername(): String = member.githubId

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    companion object {
        private const val DEFAULT_PASSWORD = "password"
    }
}
