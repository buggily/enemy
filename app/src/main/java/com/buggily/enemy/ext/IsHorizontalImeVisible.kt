package com.buggily.enemy.ext

import android.content.res.Configuration
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.runtime.Composable

val Configuration.isHorizontalImeVisible: Boolean

    @Composable
    get() = orientationCompat == Orientation.Horizontal && WindowInsets.isImeVisible
