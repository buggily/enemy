package com.buggily.enemy.core.ext

val Int.isNonNegative: Boolean
    get() = !isNegative

val Float.isNonNegative: Boolean
    get() = !isNegative
