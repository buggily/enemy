package com.buggily.enemy.core.ui.ui

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun IconFloatingActionButton(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    shape: Shape = FloatingActionButtonDefaults.shape,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        shape = shape,
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

@Composable
fun SmallIconFloatingActionButton(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    shape: Shape = FloatingActionButtonDefaults.shape,
    onClick: () -> Unit,
) {
    SmallFloatingActionButton(
        shape = shape,
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
