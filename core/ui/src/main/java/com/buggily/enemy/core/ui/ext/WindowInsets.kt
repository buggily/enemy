package com.buggily.enemy.core.ui.ext

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.ui.unit.dp

val WindowInsets.Companion.ZERO: WindowInsets
    get() = WindowInsets(
        left = 0.dp,
        top = 0.dp,
        right = 0.dp,
        bottom = 0.dp,
    )
