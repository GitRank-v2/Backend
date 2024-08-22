package com.dragonguard.core.domain.member

import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberMapper: MemberMapper,
) {
    @Transactional
    fun joinIfNone(
        githubId: String,
        name: String,
        profileImage: String,
        userRequest: OAuth2UserRequest,
    ): Member {
        if (!memberRepository.existsByGithubId(githubId)) {
            memberRepository.save(
                memberMapper.toEntity(
                    name,
                    githubId,
                    profileImage,
                ),
            )
        }

        val member: Member =
            getEntityByGithubId(githubId)

        if (member.hasNoAuthStep()) {
            member.join(name, profileImage)
        }
        member.updateGithubToken(userRequest.accessToken.tokenValue)
        return member
    }

    private fun getEntityByGithubId(githubId: String) = memberRepository.findByGithubId(githubId) ?: throw EntityNotFoundException()
}
