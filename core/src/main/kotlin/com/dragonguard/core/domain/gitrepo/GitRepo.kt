package com.dragonguard.core.domain.gitrepo

import com.dragonguard.core.global.audit.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.CollectionTable
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import org.hibernate.annotations.SoftDelete

@Entity
@SoftDelete
class GitRepo(
    val name: String,
    @CollectionTable
    @ElementCollection
    val sparkLine: List<Int>,
    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE], mappedBy = "gitRepo")
    val gitRepoMembers: MutableList<GitRepoMember> = mutableListOf(),
) : BaseEntity() {
    constructor(name: String, sparkLine: List<Int>, gitRepoMember: GitRepoMember) : this(name, sparkLine) {
        organize(gitRepoMember)
    }

    private fun organize(gitRepoMember: GitRepoMember) {
        gitRepoMembers.add(gitRepoMember)
    }
}
