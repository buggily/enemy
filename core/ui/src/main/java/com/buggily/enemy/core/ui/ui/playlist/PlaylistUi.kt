package com.buggily.enemy.core.ui.ui.playlist

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.buggily.enemy.core.ui.ui.ItemRow
import com.buggily.enemy.core.ui.ui.ItemTextMajor
import com.buggily.enemy.core.ui.ui.ItemTextMinor
import com.buggily.enemy.domain.playlist.PlaylistUi

@Composable
fun PlaylistItem(
    playlist: PlaylistUi,
    onClick: () -> Unit,
) {
    ItemRow(onClick) {
        PlaylistItemContent(playlist)
    }
}

@Composable
fun PlaylistItem(
    playlist: PlaylistUi,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    ItemRow(
        onClick = onClick,
        onLongClick = onLongClick,
    ) {
        PlaylistItemContent(playlist)
    }
}

@Composable
fun PlaylistItem(
    nameText: String,
    modificationText: String,
) {
    ItemRow {
        PlaylistItemContent(
            nameText = nameText,
            modificationText = modificationText,
        )
    }
}

@Composable
private fun RowScope.PlaylistItemContent(
    playlist: PlaylistUi,
) {
    ItemTextMajor(
        text = playlist.nameText,
        textAlign = TextAlign.Start,
        modifier = Modifier.weight(1f),
    )

    ItemTextMinor(
        text = playlist.modificationText,
        textAlign = TextAlign.End,
        modifier = Modifier.weight(1f),
    )
}

@Composable
private fun RowScope.PlaylistItemContent(
    nameText: String,
    modificationText: String,
) {
    ItemTextMajor(
        text = nameText,
        textAlign = TextAlign.Start,
        modifier = Modifier.weight(1f),
    )

    ItemTextMinor(
        text = modificationText,
        textAlign = TextAlign.End,
        modifier = Modifier.weight(1f),
    )
}
