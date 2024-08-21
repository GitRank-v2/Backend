package com.dragonguard.core.domain.gitorg

import com.dragonguard.core.domain.member.Member
import com.dragonguard.core.global.audit.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import org.hibernate.annotations.SoftDelete

@Entity
@SoftDelete
class GitOrg(
    @Column(nullable = false, unique = true)
    val name: String,
    val profileImage: String,
) : BaseEntity() {
    constructor(name: String, profileImage: String, member: Member) : this(name, profileImage) {
        organize(member)
    }

    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE], mappedBy = "gitOrg")
    val gitOrgMembers: MutableList<GitOrgMember> = mutableListOf()

    private fun organize(member: Member) {
        val gitOrgMember = GitOrgMember(member.id!!, this)
        gitOrgMembers.add(gitOrgMember)
    }
}
