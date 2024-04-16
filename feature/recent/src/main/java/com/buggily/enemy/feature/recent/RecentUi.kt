package com.buggily.enemy.feature.recent

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Surface
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
import com.buggily.enemy.core.ui.ext.playsText
import com.buggily.enemy.core.ui.ui.SingleLineText
import com.buggily.enemy.core.ui.ui.album.AlbumItem
import com.buggily.enemy.core.ui.ui.track.TrackItem
import com.buggily.enemy.core.ui.ui.track.TrackItemWithEndText
import com.buggily.enemy.data.track.TrackWithMetadata
import com.buggily.enemy.core.ui.R as CR

@Composable
fun RecentScreen(
    viewModel: RecentViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState: RecentUiState by viewModel.uiState.collectAsStateWithLifecycle()

    val recentTracks: LazyPagingItems<TrackWithMetadata> =
        viewModel.recentTracks.collectAsLazyPagingItems()

    val popularTracks: LazyPagingItems<TrackWithMetadata> =
        viewModel.popularTracks.collectAsLazyPagingItems()

    Box(modifier) {
        RecentScreen(
            recentTracks = recentTracks,
            popularTracks = popularTracks,
            uiState = uiState,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
fun RecentScreen(
    uiState: RecentUiState,
    recentTracks: LazyPagingItems<TrackWithMetadata>,
    popularTracks: LazyPagingItems<TrackWithMetadata>,
    modifier: Modifier = Modifier,
) {
    RecentScreen(
        recentTracks = recentTracks,
        popularTracks = popularTracks,
        trackState = uiState.trackState,
        modifier = modifier,
    )
}

@Composable
private fun RecentScreen(
    recentTracks: LazyPagingItems<TrackWithMetadata>,
    popularTracks: LazyPagingItems<TrackWithMetadata>,
    trackState: RecentUiState.TrackState,
    modifier: Modifier = Modifier,
) {
    when (LocalWindowSizeClass.current.heightSizeClass) {
        WindowHeightSizeClass.Compact -> RecentScreenCompact(
            recentTracks = recentTracks,
            popularTracks = popularTracks,
            trackState = trackState,
            modifier = modifier,
        )

        else -> RecentScreenMedium(
            recentTracks = recentTracks,
            popularTracks = popularTracks,
            trackState = trackState,
            modifier = modifier,
        )
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun RecentScreenCompact(
    recentTracks: LazyPagingItems<TrackWithMetadata>,
    popularTracks: LazyPagingItems<TrackWithMetadata>,
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
            RecentTracksRow(
                tracks = recentTracks,
                trackState = trackState,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(5f),
            )
        }

        item {
            Spacer(
                modifier = Modifier.padding(dimensionResource(CR.dimen.padding_large)),
            )
        }

        item {
            PopularTracksHeader(
                modifier = Modifier.padding(dimensionResource(CR.dimen.padding_large)),
            )
        }

        stickyHeader {
            PopularTracksStickyHeader(
                modifier = Modifier.fillMaxWidth(),
            )
        }

        items(
            count = popularTracks.itemCount,
            key = popularTracks.itemKey { it.track.id },
        ) {
            when (val track: TrackWithMetadata? = popularTracks[it]) {
                is TrackWithMetadata -> TrackItem(
                    track = track,
                    trackState = trackState,
                )

                else -> Unit
            }
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun RecentScreenMedium(
    recentTracks: LazyPagingItems<TrackWithMetadata>,
    popularTracks: LazyPagingItems<TrackWithMetadata>,
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
            RecentTracksRow(
                tracks = recentTracks,
                trackState = trackState,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f),
            )
        }

        item {
            Spacer(
                modifier = Modifier.padding(dimensionResource(CR.dimen.padding_large)),
            )
        }

        item {
            PopularTracksHeader(
                modifier = Modifier.padding(dimensionResource(CR.dimen.padding_large)),
            )
        }

        stickyHeader {
            PopularTracksStickyHeader(
                modifier = Modifier.fillMaxWidth(),
            )
        }

        items(
            count = popularTracks.itemCount,
            key = popularTracks.itemKey { it.track.id },
        ) {
            when (val track: TrackWithMetadata? = popularTracks[it]) {
                is TrackWithMetadata -> TrackItem(
                    track = track,
                    trackState = trackState,
                )

                else -> Unit
            }
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
private fun RecentTracksRow(
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
                is TrackWithMetadata -> TrackAlbumItem(
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
private fun PopularTracksHeader(
    modifier: Modifier = Modifier,
) {
    SingleLineText(
        text = stringResource(R.string.recent_tracks_popularity),
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.headlineSmall,
        modifier = modifier,
    )
}

@Composable
private fun PopularTracksStickyHeader(
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier,
    ) {
        TrackItem(
            nameText = stringResource(R.string.recent_tracks_popularity_name),
            artistText = stringResource(R.string.recent_tracks_popularity_artist),
            durationText = stringResource(R.string.recent_tracks_popularity_plays),
        )
    }
}

@Composable
private fun TrackAlbumItem(
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

@Composable
private fun TrackItem(
    track: TrackWithMetadata,
    trackState: RecentUiState.TrackState,
) {
    TrackItemWithEndText(
        track = track.track,
        onClick = { trackState.onClick(track.track) },
        endText = track.playsText,
    )
}
