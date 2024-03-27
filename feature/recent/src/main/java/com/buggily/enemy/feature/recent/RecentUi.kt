package com.buggily.enemy.feature.recent

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.buggily.enemy.core.ui.LocalWindowSizeClass
import com.buggily.enemy.core.ui.ext.nameText
import com.buggily.enemy.core.ui.ui.SingleLineText
import com.buggily.enemy.core.ui.ui.album.AlbumItem
import com.buggily.enemy.data.track.TrackWithMetadata
import com.buggily.enemy.core.ui.R as CR

@Composable
fun RecentScreen(
    viewModel: RecentViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState: RecentUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val tracks: LazyPagingItems<TrackWithMetadata> = viewModel.tracks.collectAsLazyPagingItems()

    Box(modifier) {
        RecentScreen(
            tracks = tracks,
            uiState = uiState,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
fun RecentScreen(
    uiState: RecentUiState,
    tracks: LazyPagingItems<TrackWithMetadata>,
    modifier: Modifier = Modifier,
) {
    RecentScreen(
        tracks = tracks,
        trackState = uiState.trackState,
        modifier = modifier,
    )
}

@Composable
private fun RecentScreen(
    tracks: LazyPagingItems<TrackWithMetadata>,
    trackState: RecentUiState.TrackState,
    modifier: Modifier = Modifier,
) {
    when (LocalWindowSizeClass.current.heightSizeClass) {
        WindowHeightSizeClass.Compact -> RecentScreenCompact(
            tracks = tracks,
            trackState = trackState,
            modifier = modifier,
        )

        else -> RecentScreenMedium(
            tracks = tracks,
            trackState = trackState,
            modifier = modifier,
        )
    }
}

@Composable
private fun RecentScreenCompact(
    tracks: LazyPagingItems<TrackWithMetadata>,
    trackState: RecentUiState.TrackState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        item {
            RecentTracksHeader(
                modifier = Modifier.padding(dimensionResource(CR.dimen.padding_large)),
            )
        }

        item {
            TracksRow(
                tracks = tracks,
                trackState = trackState,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(5f),
            )
        }
    }
}

@Composable
private fun RecentScreenMedium(
    tracks: LazyPagingItems<TrackWithMetadata>,
    trackState: RecentUiState.TrackState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        item {
            RecentTracksHeader(
                modifier = Modifier.padding(dimensionResource(CR.dimen.padding_large)),
            )
        }

        item {
            TracksRow(
                tracks = tracks,
                trackState = trackState,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f),
            )
        }
    }
}

@Composable
private fun RecentTracksHeader(
    modifier: Modifier = Modifier,
) {
    SingleLineText(
        text = stringResource(R.string.recent_tracks),
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.headlineSmall,
        modifier = modifier,
    )
}

@Composable
private fun TracksRow(
    tracks: LazyPagingItems<TrackWithMetadata>,
    trackState: RecentUiState.TrackState,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(
            space = dimensionResource(CR.dimen.padding_small),
            alignment = Alignment.Start,
        ),
        verticalAlignment = Alignment.Top,
        contentPadding = WindowInsets(
            left = dimensionResource(CR.dimen.padding_large),
            right = dimensionResource(CR.dimen.padding_large),
        ).asPaddingValues(),
        modifier = modifier,
    ) {
        items(
            count = tracks.itemCount,
            key = tracks.itemKey { it.track.id },
        ) {
            when (val track: TrackWithMetadata? = tracks[it]) {
                is TrackWithMetadata -> TrackItem(
                    track = track,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                        .clickable { trackState.onClick(track.track) },
                )

                else -> Unit
            }
        }
    }
}

@Composable
private fun TrackItem(
    track: TrackWithMetadata,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(CR.dimen.padding_medium),
            alignment = Alignment.Top,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        AlbumItem(
            album = track.track.album,
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f),
        )

        SingleLineText(
            text = track.track.nameText,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}
