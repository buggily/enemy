package com.buggily.enemy.core.model

import kotlin.time.Duration

data class Runtime(
    val text: String,
    val duration: Duration,
) {

    val inWholeMilliseconds: Long
        get() = duration.inWholeMilliseconds

    val inWholeSeconds: Long
        get() = duration.inWholeSeconds

    companion object {
        val default: Runtime
            get() = Runtime(
                text = String(),
                duration = Duration.ZERO,
            )
    }
}
