package com.buggily.enemy.feature.playlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.buggily.enemy.core.ui.LocalWindowSizeClass
import com.buggily.enemy.core.ui.ext.floatResource
import com.buggily.enemy.core.ui.ext.peekFirst
import com.buggily.enemy.core.ui.ui.ArtImage
import com.buggily.enemy.core.ui.ui.PlayButton
import com.buggily.enemy.core.ui.ui.track.TrackItem
import com.buggily.enemy.domain.playlist.PlaylistUi
import com.buggily.enemy.domain.track.TrackUi
import com.buggily.enemy.domain.track.TrackWithIndexUi
import com.buggily.enemy.core.ui.R as CR

@Composable
fun PlaylistScreen(
    viewModel: PlaylistViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState: PlaylistUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val tracks: LazyPagingItems<TrackWithIndexUi> = viewModel.tracks.collectAsLazyPagingItems()

    Box(modifier) {
        PlaylistScreen(
            uiState = uiState,
            tracks = tracks,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun PlaylistScreen(
    uiState: PlaylistUiState,
    tracks: LazyPagingItems<TrackWithIndexUi>,
    modifier: Modifier = Modifier,
) {
    PlaylistScreen(
        playlist = uiState.playlist,
        playlistState = uiState.playlistState,
        trackState = uiState.trackState,
        tracks = tracks,
        modifier = modifier,
    )
}

@Composable
private fun PlaylistScreen(
    playlist: PlaylistUi?,
    playlistState: PlaylistUiState.PlaylistState,
    trackState: PlaylistUiState.TrackState,
    tracks: LazyPagingItems<TrackWithIndexUi>,
    modifier: Modifier = Modifier,
) {
    val track: TrackUi? = remember(tracks.itemSnapshotList) { tracks.peekFirst()?.track }

    when (LocalWindowSizeClass.current.heightSizeClass) {
        WindowHeightSizeClass.Compact -> PlaylistScreenCompact(
            track = track,
            playlist = playlist,
            trackState = trackState,
            playlistState = playlistState,
            tracks = tracks,
            modifier = modifier,
        )

        else -> PlaylistScreenMedium(
            track = track,
            playlist = playlist,
            trackState = trackState,
            playlistState = playlistState,
            tracks = tracks,
            modifier = modifier,
        )
    }
}

@Composable
private fun PlaylistScreenCompact(
    track: TrackUi?,
    playlist: PlaylistUi?,
    trackState: PlaylistUiState.TrackState,
    playlistState: PlaylistUiState.PlaylistState,
    tracks: LazyPagingItems<TrackWithIndexUi>,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top,
        modifier = modifier,
    ) {
        val itemModifier: Modifier = Modifier.fillMaxHeight()

        when (playlist) {
            is PlaylistUi -> PlaylistHeader(
                track = track,
                playlist = playlist,
                playlistState = playlistState,
                modifier = itemModifier.weight(1f),
            )
        }

        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            contentPadding = WindowInsets.systemBars.asPaddingValues(),
            modifier = itemModifier.weight(2f),
        ) {
            items(tracks.itemCount) {
                when (val trackWithIndex: TrackWithIndexUi? = tracks[it]) {
                    is TrackWithIndexUi -> TrackItem(
                        track = trackWithIndex.track,
                        onClick = { trackState.onClick(trackWithIndex) },
                        onLongClick = { trackState.onLongClick(trackWithIndex) },
                    )

                    else -> Unit
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun PlaylistScreenMedium(
    track: TrackUi?,
    playlist: PlaylistUi?,
    trackState: PlaylistUiState.TrackState,
    playlistState: PlaylistUiState.PlaylistState,
    tracks: LazyPagingItems<TrackWithIndexUi>,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            contentPadding = WindowInsets.navigationBars.asPaddingValues(),
            modifier = modifier,
        ) {
            when (playlist) {
                is PlaylistUi -> stickyHeader {
                    PlaylistHeader(
                        track = track,
                        playlist = playlist,
                        playlistState = playlistState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                    )
                }
            }

            items(tracks.itemCount) {
                when (val trackWithIndex: TrackWithIndexUi? = tracks[it]) {
                    is TrackWithIndexUi -> TrackItem(
                        track = trackWithIndex.track,
                        onClick = { trackState.onClick(trackWithIndex) },
                        onLongClick = { trackState.onLongClick(trackWithIndex) },
                    )

                    else -> Unit
                }
            }
        }
    }
}

@Composable
private fun PlaylistHeader(
    track: TrackUi?,
    playlist: PlaylistUi,
    playlistState: PlaylistUiState.PlaylistState,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        modifier = modifier,
    ) {
        when (track) {
            is TrackUi -> PlaylistHeaderBackground(
                track = track,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(floatResource(CR.dimen.alpha_low)),
            )
        }

        PlaylistHeaderForeground(
            playlist = playlist,
            playlistState = playlistState,
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(dimensionResource(CR.dimen.padding_large)),
        )
    }
}

@Composable
private fun PlaylistHeaderBackground(
    track: TrackUi,
    modifier: Modifier,
) {
    ArtImage(
        artable = track.album,
        contentScale = ContentScale.Crop,
        modifier = modifier,
    )
}

@Composable
private fun PlaylistHeaderForeground(
    playlist: PlaylistUi,
    playlistState: PlaylistUiState.PlaylistState,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom,
        modifier = modifier,
    ) {
        Text(
            text = playlist.nameText,
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(Modifier.weight(1f))

        PlayButton(
            onClick = playlistState.onStartClick,
            contentModifier = Modifier.size(dimensionResource(CR.dimen.icon_medium)),
        )
    }
}
