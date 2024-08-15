package com.dragonguard.core.domain.email

import com.dragonguard.core.global.audit.BaseEntity
import jakarta.persistence.Entity
import org.hibernate.annotations.SoftDelete

@Entity
@SoftDelete
class Email(
    val memberId: Long,
    val code: Int,
) : BaseEntity()
