package com.buggily.enemy.core.data

import kotlinx.datetime.Instant

data class InstantWithMetadata(
    override val value: Instant,
    override val text: String,
): Instantable
