package com.dragonguard.core.domain.gitrepo

import jakarta.persistence.Embeddable

@Embeddable
class GitRepoContribution(
    var commits: Int,
    var additions: Int,
    var deletions: Int,
)
