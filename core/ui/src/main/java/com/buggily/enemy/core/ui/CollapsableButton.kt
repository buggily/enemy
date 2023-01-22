package com.buggily.enemy.core.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.ui.geometry.Offset as GeometryOffset

object CollapsableButton {

    data class Offset(
        private val alignment: Alignment,
        private val distance: Float,
        private val intervalCount: Int,
    ) {

        fun get(index: Int): GeometryOffset {
            val interval: Int = span / intervalCount.dec().coerceAtLeast(1)
            val degrees: Int = offset + interval * index
            val radians: Float = Math.toRadians(degrees.toDouble()).toFloat()

            return GeometryOffset(
                x = cos(radians),
                y = -sin(radians),
            ) * distance
        }

        private val span: Int
            get() = with(range) { last - first }

        private val offset: Int
            get() = range.first

        private val range: IntRange
            get() = with(primitiveRange) {
                val start: Int = start * right
                val endInclusive: Int = endInclusive * right

                start..endInclusive
            }

        private val primitiveRange: IntRange
            get() = when (alignment) {
                Alignment.TopStart -> 1..2
                Alignment.TopCenter -> 0..2
                Alignment.TopEnd -> 0..1
                Alignment.CenterStart -> 1..3
                Alignment.Center -> 0..4
                Alignment.CenterEnd -> 3..5
                Alignment.BottomStart -> 2..3
                Alignment.BottomCenter -> 2..4
                Alignment.BottomEnd -> 3..4
                else -> 0..0
            }

        private companion object {
            private const val circle = 360
            private const val right = circle / 4
        }
    }
}

@Composable
fun CollapsableButton(
    isCollapsable: Boolean,
    alignment: Alignment,
    modifier: Modifier = Modifier,
    contents: List<@Composable () -> Unit>,
    content: @Composable () -> Unit,
) {
    val density: Density = LocalDensity.current
    val intervalCount: Int = contents.size

    BoxWithConstraints(modifier) {
        val distance: Float = minOf(maxWidth.value, maxHeight.value)
        val composableModifier: Modifier = Modifier.align(alignment)

        val buttonOffset = CollapsableButton.Offset(
            alignment = alignment,
            distance = distance,
            intervalCount = intervalCount,
        )

        contents.forEachIndexed { index, it ->
            val offset: Offset = buttonOffset.get(index)

            val xDp: Dp = offset.x.dp
            val yDp: Dp = offset.y.dp

            val intOffset = IntOffset(
                x = with(density) { xDp.roundToPx() },
                y = with(density) { yDp.roundToPx() },
            )

            AnimatedVisibility(
                visible = isCollapsable,
                enter = fadeIn() + slideIn { intOffset },
                exit = fadeOut() + slideOut { intOffset },
                modifier = composableModifier.offset(
                    x = -xDp,
                    y = -yDp,
                ),
            ) { it() }
        }

        Box(composableModifier) {
            content()
        }
    }
}
