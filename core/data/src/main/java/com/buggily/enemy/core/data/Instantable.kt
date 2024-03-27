package com.buggily.enemy.core.data

import kotlinx.datetime.Instant

interface Instantable {
    val value: Instant
    val text: String
}