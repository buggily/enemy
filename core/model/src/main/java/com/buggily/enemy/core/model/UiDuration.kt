package com.buggily.enemy.core.model

import kotlin.time.Duration

data class UiDuration(
    val value: Duration,
    val text: String,
) {

    val inWholeMilliseconds: Long
        get() = value.inWholeMilliseconds

    val inWholeSeconds: Long
        get() = value.inWholeSeconds

    companion object {
        val default: UiDuration
            get() = UiDuration(
                text = String(),
                value = Duration.ZERO,
            )
    }
}
