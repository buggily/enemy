package com.buggily.enemy.core.ext

import kotlin.time.Duration

fun Duration.isNonNegative(): Boolean = !isNegative()
