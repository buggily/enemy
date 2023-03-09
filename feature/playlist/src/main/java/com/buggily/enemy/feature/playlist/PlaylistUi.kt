package com.buggily.enemy.feature.playlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.paging.compose.items
import com.buggily.enemy.core.model.playlist.Playlist
import com.buggily.enemy.core.model.track.Track
import com.buggily.enemy.core.ui.composable.ArtImage
import com.buggily.enemy.core.ui.ext.artistText
import com.buggily.enemy.core.ui.ext.durationText
import com.buggily.enemy.core.ui.ext.floatResource
import com.buggily.enemy.core.ui.ext.nameText
import com.buggily.enemy.core.ui.ext.peekFirst
import com.buggily.enemy.core.ui.R.dimen as dimens

@Composable
fun PlaylistScreen(
    viewModel: PlaylistViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState: PlaylistUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val tracks: LazyPagingItems<Track> = viewModel.tracks.collectAsLazyPagingItems()

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
    tracks: LazyPagingItems<Track>,
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
@OptIn(ExperimentalFoundationApi::class)
private fun PlaylistScreen(
    playlist: Playlist?,
    trackState: PlaylistUiState.TrackState,
    tracks: LazyPagingItems<Track>,
    modifier: Modifier = Modifier,
) {
    val itemModifier: Modifier = Modifier.fillMaxWidth()
    val track: Track? = remember(tracks) { tracks.peekFirst() }

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
                        modifier = itemModifier.height(IntrinsicSize.Min),
                    )
                }
            }

            items(
                items = tracks,
                key = { it.id },
            ) {
                when (it) {
                    is Track -> TrackItem(
                        track = it,
                        modifier = itemModifier
                            .clickable { trackState.onClick(it) }
                            .padding(
                                horizontal = dimensionResource(dimens.padding_large),
                                vertical = dimensionResource(dimens.padding_large_extra),
                            ),
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
                    .alpha(floatResource(dimens.alpha_low)),
            )
        }

        PlaylistHeaderForeground(
            playlist = playlist,
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(dimensionResource(dimens.padding_large)),
        )
    }
}

@Composable
private fun PlaylistHeaderBackground(
    track: Track,
    modifier: Modifier,
) {
    ArtImage(
        artable = track,
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
