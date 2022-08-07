package com.buggily.enemy.ext

import android.content.res.Configuration
import androidx.compose.foundation.gestures.Orientation

val Configuration.orientationCompat: Orientation
    get() = when (orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> Orientation.Horizontal
        else -> Orientation.Vertical
    }
