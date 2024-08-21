package com.dragonguard.core.config.security.oauth.user

import com.dragonguard.core.domain.member.Member
import org.springframework.stereotype.Component

@Component
class UserPrincipleMapper {
    fun mapToLoginUser(user: Member): UserPrinciple {
        val attributes = mutableMapOf<String, Any>()
        attributes["id"] = user.id!!
        return UserPrinciple(user, user.getAuthorityByRoles(), attributes)
    }
}
