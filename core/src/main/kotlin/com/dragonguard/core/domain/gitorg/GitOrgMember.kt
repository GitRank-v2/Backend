package com.dragonguard.core.domain.gitorg

import com.dragonguard.core.global.audit.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.SoftDelete

@Entity
@SoftDelete
class GitOrgMember(
    val memberId: Long,
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var gitOrg: GitOrg,
) : BaseEntity()
