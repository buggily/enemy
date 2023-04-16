package com.buggily.enemy.tracks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.buggily.enemy.core.ui.ext.artistText
import com.buggily.enemy.core.ui.ext.durationText
import com.buggily.enemy.core.ui.ext.floatResource
import com.buggily.enemy.core.ui.ext.nameText
import com.buggily.enemy.data.track.Track
import com.buggily.enemy.feature.tracks.R
import com.buggily.enemy.core.ui.R.dimen as dimens

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
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(dimens.padding_large)),
        )
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun LazyItemScope.TrackItem(
    track: Track,
    onClick: () -> Unit,
) {
    TrackItem(
        track = track,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = dimensionResource(dimens.padding_large_extra_extra))
            .clickable { onClick() }
            .animateItemPlacement()
            .padding(
                horizontal = dimensionResource(dimens.padding_large),
                vertical = dimensionResource(dimens.padding_large_extra),
            )
    )
}

@Composable
private fun TrackItem(
    track: Track,
    modifier: Modifier = Modifier,
) {
    TrackItem(
        nameText = track.nameText,
        artistText = track.artistText,
        durationText = track.durationText,
        modifier = modifier,
    )
}

@Composable
private fun TrackItem(
    nameText: String,
    artistText: String,
    durationText: String,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = dimensionResource(dimens.padding_large),
            alignment = Alignment.Start,
        ),
        verticalAlignment = Alignment.Top,
        modifier = modifier,
    ) {
        Text(
            text = nameText,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(2f),
        )

        Text(
            text = artistText,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .weight(2f)
                .alpha(floatResource(dimens.alpha_medium)),
        )

        Text(
            text = durationText,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .weight(1f)
                .alpha(floatResource(dimens.alpha_medium)),
        )
    }
}
