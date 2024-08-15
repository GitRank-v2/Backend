package com.dragonguard.core.domain.contribution

import com.dragonguard.core.global.audit.BaseEntity
import jakarta.persistence.Entity
import org.hibernate.annotations.SoftDelete

@Entity
@SoftDelete
class Contribution(
    val contributionType: ContributionType,
    var amount: Int,
    val year: Int,
    val memberId: Long,
) : BaseEntity()
