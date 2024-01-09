package com.buggily.enemy.core.ui.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.buggily.enemy.core.ui.R

@Composable
fun ItemRow(
    content: @Composable RowScope.() -> Unit,
) {
    ItemRow(
        content = content,
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_large)),
    )
}

@Composable
fun ItemRow(
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    ItemRow(
        content = content,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(
                minWidth = Dp.Unspecified,
                minHeight = dimensionResource(R.dimen.padding_large_extra_extra),
            )
            .padding(
                horizontal = dimensionResource(R.dimen.padding_large),
                vertical = dimensionResource(R.dimen.padding_large_extra),
            )
            .clickable { onClick() },
    )
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun ItemRow(
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    ItemRow(
        content = content,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(
                minWidth = Dp.Unspecified,
                minHeight = dimensionResource(R.dimen.padding_large_extra_extra),
            )
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
            )
            .padding(
                horizontal = dimensionResource(R.dimen.padding_large),
                vertical = dimensionResource(R.dimen.padding_large_extra),
            ),
    )
}

@Composable
private fun ItemRow(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = dimensionResource(R.dimen.padding_large),
            alignment = Alignment.Start,
        ),
        verticalAlignment = Alignment.CenterVertically,
        content = content,
        modifier = modifier,
    )
}
