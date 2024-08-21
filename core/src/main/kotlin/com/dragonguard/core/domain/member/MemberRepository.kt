package com.dragonguard.core.domain.member

import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByGithubId(githubId: String): Member?

    @Modifying
    @Transactional
    @Query("UPDATE Member m SET m.refreshToken = :token WHERE m.id = :id")
    fun updateRefreshToken(
        id: Long,
        token: String,
    )

    fun existsByGithubId(githubId: String): Boolean
}
