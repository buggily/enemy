package com.buggily.enemy.core.ui.ui

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.buggily.enemy.core.ui.R

@Composable
fun PlayButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
) {
    IconFloatingActionButton(
        shape = CircleShape,
        painter = painterResource(R.drawable.play),
        contentDescription = stringResource(R.string.play),
        onClick = onClick,
        modifier = modifier,
        contentModifier = contentModifier,
    )
}

@Composable
fun SmallPlayButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
) {
    SmallIconFloatingActionButton(
        shape = CircleShape,
        painter = painterResource(R.drawable.play),
        contentDescription = stringResource(R.string.play),
        onClick = onClick,
        modifier = modifier,
        contentModifier = contentModifier,
    )
}
