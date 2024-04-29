package com.buggily.enemy.tracks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.buggily.enemy.core.ui.ui.track.TrackItem
import com.buggily.enemy.domain.track.TrackUi
import com.buggily.enemy.feature.tracks.R

@Composable
fun TracksScreen(
    viewModel: TracksViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState: TracksUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val tracks: LazyPagingItems<TrackUi> = viewModel.tracks.collectAsLazyPagingItems()

    Box(modifier) {
        TracksScreen(
            uiState = uiState,
            tracks = tracks,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun TracksScreen(
    uiState: TracksUiState,
    tracks: LazyPagingItems<TrackUi>,
    modifier: Modifier,
) {
    TracksScreen(
        trackState = uiState.trackState,
        tracks = tracks,
        modifier = modifier,
    )
}

@Composable
private fun TracksScreen(
    trackState: TracksUiState.TrackState,
    tracks: LazyPagingItems<TrackUi>,
    modifier: Modifier = Modifier,
) {
    TracksColumn(
        trackState = trackState,
        tracks = tracks,
        modifier = modifier,
    )
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun TracksColumn(
    trackState: TracksUiState.TrackState,
    tracks: LazyPagingItems<TrackUi>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        stickyHeader { TracksHeader(Modifier.fillMaxWidth()) }

        items(
            count = tracks.itemCount,
            key = tracks.itemKey { it.id },
        ) {
            when (val track: TrackUi? = tracks[it]) {
                is TrackUi -> TrackItem(
                    track = track,
                    onClick = { trackState.onClick(track) },
                    onLongClick = { trackState.onLongClick(track) },
                )

                else -> Unit
            }
        }
    }
}

@Composable
private fun TracksHeader(
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier,
    ) {
        TrackItem(
            nameText = stringResource(R.string.tracks_name),
            artistNameText = stringResource(R.string.tracks_artist),
            durationText = stringResource(R.string.tracks_duration),
        )
    }
}
