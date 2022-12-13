package com.buggily.enemy.core.ui

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun IconFloatingActionButton(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            modifier = contentModifier,
        )
    }
}
