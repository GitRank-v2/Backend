package com.dragonguard.core.domain.blockchain

import com.dragonguard.core.domain.contribution.ContributionType
import com.dragonguard.core.global.audit.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.hibernate.annotations.SoftDelete
import java.math.BigInteger

@Entity
@SoftDelete
class Blockchain(
    @Column(nullable = false, unique = true)
    val address: String,
    val contributionType: ContributionType,
    val memberId: Long,
    val amount: BigInteger,
    val transactionHash: String,
) : BaseEntity()
