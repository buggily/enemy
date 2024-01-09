package com.buggily.enemy.core.ui.ui.track

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.buggily.enemy.core.data.Trackable
import com.buggily.enemy.core.ui.ext.artistText
import com.buggily.enemy.core.ui.ext.durationText
import com.buggily.enemy.core.ui.ext.nameText
import com.buggily.enemy.core.ui.ext.trackText
import com.buggily.enemy.core.ui.ui.ItemRow
import com.buggily.enemy.core.ui.ui.ItemTextMajor
import com.buggily.enemy.core.ui.ui.ItemTextMinor
import com.buggily.enemy.data.track.Track

@Composable
fun TrackItem(
    track: Trackable,
    onClick: () -> Unit,
) {
    ItemRow(onClick) {
        TrackItemContent(track)
    }
}

@Composable
fun TrackItem(
    track: Trackable,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    ItemRow(
        onClick = onClick,
        onLongClick = onLongClick,
    ) {
        TrackItemContent(track)
    }
}

@Composable
fun TrackItem(
    nameText: String,
    artistText: String,
    durationText: String,
) {
    ItemRow {
        TrackItemContent(
            nameText = nameText,
            artistText = artistText,
            durationText = durationText,
        )
    }
}

@Composable
private fun RowScope.TrackItemContent(
    track: Trackable,
) {
    ItemTextMajor(
        text = track.nameText,
        textAlign = TextAlign.Start,
        modifier = Modifier.weight(1f),
    )

    ItemTextMinor(
        text = track.artistText,
        textAlign = TextAlign.Start,
        modifier = Modifier.weight(1f),
    )

    ItemTextMinor(
        text = track.durationText,
        textAlign = TextAlign.End,
    )
}

@Composable
private fun RowScope.TrackItemContent(
    nameText: String,
    artistText: String,
    durationText: String,
) {
    ItemTextMajor(
        text = nameText,
        textAlign = TextAlign.Start,
        modifier = Modifier.weight(1f),
    )

    ItemTextMinor(
        text = artistText,
        textAlign = TextAlign.Start,
        modifier = Modifier.weight(1f),
    )

    ItemTextMinor(
        text = durationText,
        textAlign = TextAlign.End,
    )
}

@Composable
fun AlbumTrackItem(
    track: Track,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    ItemRow(
        onClick = onClick,
        onLongClick = onLongClick,
    ) {
        AlbumTrackItemContent(track)
    }
}

@Composable
private fun RowScope.AlbumTrackItemContent(
    track: Track,
) {
    ItemTextMinor(
        text = track.trackText,
        textAlign = TextAlign.Start,
    )

    ItemTextMajor(
        text = track.nameText,
        textAlign = TextAlign.Start,
        modifier = Modifier.weight(1f),
    )

    ItemTextMinor(
        text = track.durationText,
        textAlign = TextAlign.End,
    )
}
