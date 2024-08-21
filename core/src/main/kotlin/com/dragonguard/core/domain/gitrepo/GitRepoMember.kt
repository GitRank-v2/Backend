package com.dragonguard.core.domain.gitrepo

import com.dragonguard.core.domain.member.Member
import com.dragonguard.core.global.audit.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.SoftDelete

@Entity
@SoftDelete
class GitRepoMember(
    @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    var gitRepo: GitRepo,
    @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    var member: Member,
    @Embedded
    var gitRepoContribution: GitRepoContribution,
) : BaseEntity()
