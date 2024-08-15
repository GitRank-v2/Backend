package com.dragonguard.core.domain.member

import com.dragonguard.core.domain.organization.Organization
import com.dragonguard.core.global.audit.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.SoftDelete

@Entity
@SoftDelete
class Member(
    var name: String,
    @Column(nullable = false, unique = true)
    var githubId: String,
    var profileImage: String,
    var walletAddress: String,
    var refreshToken: String,
    var githubToken: String,
    var email: String,
) : BaseEntity() {
    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    var organization: Organization? = null

    @JoinColumn
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private val _authStep: MutableList<AuthStep> = mutableListOf(AuthStep.GITHUB)
    val authStep: List<AuthStep>
        get() = _authStep.toList()

    @JoinColumn
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private val _roles: MutableList<Role> = mutableListOf(Role.ROLE_USER)
    val roles: List<Role>
        get() = _roles.toList()

    fun addAuthStep(authStep: AuthStep) {
        _authStep.add(authStep)
    }

    fun addRole(role: Role) {
        _roles.add(role)
    }

    fun organize(organization: Organization) {
        this.organization = organization
    }
}
