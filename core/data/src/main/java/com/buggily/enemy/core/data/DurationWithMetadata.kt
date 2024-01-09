package com.buggily.enemy.core.data

import kotlin.time.Duration

data class DurationWithMetadata(
    override val value: Duration,
    override val text: String,
): Durationable {

    val inWholeMilliseconds: Long
        get() = value.inWholeMilliseconds

    val inWholeSeconds: Long
        get() = value.inWholeSeconds

    companion object {
        val ZERO: DurationWithMetadata = DurationWithMetadata(
            text = String(),
            value = Duration.ZERO,
        )
    }
}
