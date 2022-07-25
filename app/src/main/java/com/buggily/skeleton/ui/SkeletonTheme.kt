package com.buggily.skeleton.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable

@Composable
fun SkeletonTheme(
    content: @Composable () -> Unit,
) {
    val typography = Typography()
    val shapes = Shapes()

    val colors: Colors = if (isSystemInDarkTheme()) {
        darkColors()
    } else {
        lightColors()
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content,
    )
}