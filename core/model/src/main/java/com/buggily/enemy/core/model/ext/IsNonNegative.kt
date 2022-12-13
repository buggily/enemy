package com.buggily.enemy.core.model.ext

val Int.isNonNegative: Boolean
    get() = !isNegative
