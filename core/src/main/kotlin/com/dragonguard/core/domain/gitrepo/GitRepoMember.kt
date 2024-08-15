package com.dragonguard.core.domain.gitrepo

import com.dragonguard.core.domain.member.Member
import com.dragonguard.core.global.audit.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.SoftDelete

@Entity
@SoftDelete
class GitRepoMember(
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var gitRepo: GitRepo,
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var member: Member,
    @Embedded
    var gitRepoContribution: GitRepoContribution,
) : BaseEntity()
