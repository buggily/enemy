package com.buggily.enemy.core.data

import kotlinx.datetime.Instant

data class InstantWithMetadata(
    val value: Instant,
    val text: String,
)
