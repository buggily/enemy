package com.buggily.enemy.core.ui.ui.track

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.buggily.enemy.core.ui.ui.ItemRow
import com.buggily.enemy.core.ui.ui.ItemTextMajor
import com.buggily.enemy.core.ui.ui.ItemTextMinor
import com.buggily.enemy.domain.track.TrackUi

@Composable
fun TrackItem(
    track: TrackUi,
    onClick: () -> Unit,
) {
    ItemRow(onClick) {
        TrackItemContent(track)
    }
}

@Composable
fun TrackItem(
    track: TrackUi,
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
fun TrackItemWithEndText(
    track: TrackUi,
    onClick: () -> Unit,
    endText: String,
) {
    ItemRow(onClick) {
        TrackItemContent(
            track = track,
            endText = endText,
        )
    }
}

@Composable
fun TrackItem(
    nameText: String,
    artistNameText: String,
    durationText: String,
) {
    ItemRow {
        TrackItemContent(
            startText = nameText,
            centerText = artistNameText,
            endText = durationText,
        )
    }
}

@Composable
private fun RowScope.TrackItemContent(
    track: TrackUi,
) {
    TrackItemContent(
        startText = track.nameText,
        centerText = track.artist.nameText,
        endText = track.durationText,
    )
}

@Composable
private fun RowScope.TrackItemContent(
    track: TrackUi,
    endText: String,
) {
    TrackItemContent(
        startText = track.nameText,
        centerText = track.artist.nameText,
        endText = endText,
    )
}

@Composable
private fun RowScope.TrackItemContent(
    startText: String,
    centerText: String,
    endText: String,
) {
    ItemTextMajor(
        text = startText,
        textAlign = TextAlign.Start,
        modifier = Modifier.weight(1f),
    )

    ItemTextMinor(
        text = centerText,
        textAlign = TextAlign.Start,
        modifier = Modifier.weight(1f),
    )

    ItemTextMinor(
        text = endText,
        textAlign = TextAlign.End,
    )
}

@Composable
fun AlbumTrackItem(
    track: TrackUi,
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
    track: TrackUi,
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
