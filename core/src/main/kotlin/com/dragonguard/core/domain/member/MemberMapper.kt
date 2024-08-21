package com.dragonguard.core.domain.member

import org.springframework.stereotype.Component

@Component
class MemberMapper {
    fun toEntity(
        name: String,
        githubId: String,
        profileImage: String,
    ) = Member(
        name,
        githubId,
        profileImage,
    )
}
