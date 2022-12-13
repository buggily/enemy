package com.buggily.enemy.core.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun IconButton(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
    ) {
        Icon(
            painter = painter,
            contentDescription = contentDescription,
            modifier = contentModifier,
        )
    }
}
