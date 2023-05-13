package com.buggily.enemy.core.ui.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.buggily.enemy.core.ui.R

@Composable
fun ItemCell(
    content: @Composable () -> Unit,
) {
    ItemCell(
        content = content,
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f),
    )
}

@Composable
fun ItemCell(
    onClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    ItemCell(
        content = content,
        modifier = Modifier
            .fillMaxSize()
            .defaultMinSize(
                minWidth = dimensionResource(R.dimen.item),
                minHeight = dimensionResource(R.dimen.item),
            )
            .clickable(
                onClick = onClick,
            )
            .aspectRatio(1f)
    )
}

@Composable
private fun ItemCell(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        content = content,
        modifier = modifier,
    )
}
