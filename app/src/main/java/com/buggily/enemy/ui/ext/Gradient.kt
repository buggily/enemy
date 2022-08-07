package com.buggily.enemy.ui.ext

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun Gradient(
    orientation: Orientation,
    colors: List<Color>,
    modifier: Modifier = Modifier,
) {
    val brush: Brush = when (orientation) {
        Orientation.Horizontal -> Brush.horizontalGradient(colors)
        Orientation.Vertical -> Brush.verticalGradient(colors)
    }

    Box(modifier.background(brush))
}
