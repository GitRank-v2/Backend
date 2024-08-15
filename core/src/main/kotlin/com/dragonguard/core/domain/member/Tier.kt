package com.dragonguard.core.domain.member

enum class Tier(
    private val max: Int,
) {
    SPROUT(50),
    BRONZE(200),
    SILVER(500),
    GOLD(1000),
    PLATINUM(3000),
    DIAMOND(5000),
    RUBY(10000),
    MASTER(Integer.MAX_VALUE),
    ;

    companion object {
        fun fromPoint(point: Int): Tier = entries.first { it.max > point }
    }
}
