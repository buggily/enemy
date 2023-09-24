package com.buggily.enemy.feature.playlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
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
import com.buggily.enemy.core.ui.ext.nameText
import com.buggily.enemy.core.ui.ext.peekFirst
import com.buggily.enemy.core.ui.ui.ArtImage
import com.buggily.enemy.core.ui.ui.track.PlaylistTrackItem
import com.buggily.enemy.data.playlist.Playlist
import com.buggily.enemy.data.track.Track
import com.buggily.enemy.data.track.TrackWithIndex
import com.buggily.enemy.core.ui.R as CR

@Composable
fun PlaylistScreen(
    viewModel: PlaylistViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState: PlaylistUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val tracks: LazyPagingItems<TrackWithIndex> = viewModel.tracks.collectAsLazyPagingItems()

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
    tracks: LazyPagingItems<TrackWithIndex>,
    modifier: Modifier = Modifier,
) {
    PlaylistScreen(
        playlist = uiState.playlist,
        trackState = uiState.trackState,
        tracks = tracks,
        modifier = modifier,
    )
}

@Composable
private fun PlaylistScreen(
    playlist: Playlist?,
    trackState: PlaylistUiState.TrackState,
    tracks: LazyPagingItems<TrackWithIndex>,
    modifier: Modifier = Modifier,
) {
    val track: Track? = remember(tracks.itemSnapshotList) { tracks.peekFirst()?.track }

    when (LocalWindowSizeClass.current.heightSizeClass) {
        WindowHeightSizeClass.Compact -> PlaylistScreenCompact(
            track = track,
            playlist = playlist,
            trackState = trackState,
            tracks = tracks,
            modifier = modifier,
        )

        else -> PlaylistScreenMedium(
            track = track,
            playlist = playlist,
            trackState = trackState,
            tracks = tracks,
            modifier = modifier,
        )
    }
}

@Composable
private fun PlaylistScreenCompact(
    track: Track?,
    playlist: Playlist?,
    trackState: PlaylistUiState.TrackState,
    tracks: LazyPagingItems<TrackWithIndex>,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top,
        modifier = modifier,
    ) {
        val itemModifier: Modifier = Modifier.fillMaxHeight()

        when (playlist) {
            is Playlist -> PlaylistHeader(
                track = track,
                playlist = playlist,
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
                when (val trackWithIndex: TrackWithIndex? = tracks[it]) {
                    is TrackWithIndex -> PlaylistTrackItem(
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
    track: Track?,
    playlist: Playlist?,
    trackState: PlaylistUiState.TrackState,
    tracks: LazyPagingItems<TrackWithIndex>,
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
                is Playlist -> stickyHeader {
                    PlaylistHeader(
                        track = track,
                        playlist = playlist,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                    )
                }
            }

            items(tracks.itemCount) {
                when (val trackWithIndex: TrackWithIndex? = tracks[it]) {
                    is TrackWithIndex -> PlaylistTrackItem(
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
    track: Track?,
    playlist: Playlist,
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        modifier = modifier,
    ) {
        when (track) {
            is Track -> PlaylistHeaderBackground(
                track = track,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(floatResource(CR.dimen.alpha_low)),
            )
        }

        PlaylistHeaderForeground(
            playlist = playlist,
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(dimensionResource(CR.dimen.padding_large)),
        )
    }
}

@Composable
private fun PlaylistHeaderBackground(
    track: Track,
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
    playlist: Playlist,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        Text(
            text = playlist.nameText,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.BottomEnd),
        )
    }
}
