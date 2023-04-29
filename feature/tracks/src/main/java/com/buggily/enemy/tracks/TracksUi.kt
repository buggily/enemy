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
import androidx.paging.compose.items
import com.buggily.enemy.core.ui.ui.TrackItem
import com.buggily.enemy.data.track.Track
import com.buggily.enemy.feature.tracks.R

@Composable
fun TracksScreen(
    viewModel: TracksViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState: TracksUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val tracks: LazyPagingItems<Track> = viewModel.tracks.collectAsLazyPagingItems()

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
    tracks: LazyPagingItems<Track>,
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
    tracks: LazyPagingItems<Track>,
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
    tracks: LazyPagingItems<Track>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        stickyHeader { TracksHeader(Modifier.fillMaxWidth()) }

        items(
            items = tracks,
            key = { it.id },
        ) {
            when (it) {
                is Track -> TrackItem(
                    track = it,
                    onClick = { trackState.onClick(it) },
                    onLongClick = { trackState.onLongClick(it) },
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
            nameText = stringResource(R.string.name),
            artistText = stringResource(R.string.artist),
            durationText = stringResource(R.string.duration),
        )
    }
}
