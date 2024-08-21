package com.dragonguard.core.domain.member

import com.dragonguard.core.domain.organization.Organization
import com.dragonguard.core.global.audit.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.SoftDelete
import org.springframework.security.core.authority.SimpleGrantedAuthority

@Entity
@SoftDelete
class Member(
    var name: String,
    @Column(nullable = false, unique = true)
    var githubId: String,
    var profileImage: String,
) : BaseEntity() {
    @JoinColumn
    @ManyToOne(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    var organization: Organization? = null

    @CollectionTable
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private val _authStep: MutableSet<AuthStep> = mutableSetOf()
    val authStep: Set<AuthStep>
        get() = _authStep.toSet()

    @CollectionTable
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private val _roles: MutableList<Role> = mutableListOf(Role.ROLE_USER)
    val roles: List<Role>
        get() = _roles.toList()

    var walletAddress: String? = null
    var refreshToken: String? = null
    var githubToken: String? = null
    var email: String? = null

    fun addRole(role: Role) {
        _roles.add(role)
    }

    fun organize(organization: Organization) {
        this.organization = organization
    }

    fun getAuthorityByRoles(): List<SimpleGrantedAuthority> =
        roles
            .map(Role::name)
            .map(::SimpleGrantedAuthority)
            .toList()

    fun hasNoAuthStep(): Boolean = authStep.isEmpty()

    fun updateWalletAddress(walletAddress: String) {
        this.walletAddress = walletAddress
    }

    fun updateGithubToken(githubToken: String) {
        this.githubToken = githubToken
    }

    fun updateEmail(email: String) {
        this.email = email
    }

    fun join(
        name: String,
        profileImage: String,
    ) {
        _authStep.add(AuthStep.GITHUB)
        this.name = name
        this.profileImage = profileImage
    }
}
