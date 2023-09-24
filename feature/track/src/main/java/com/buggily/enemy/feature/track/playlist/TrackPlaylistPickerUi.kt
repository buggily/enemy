package com.buggily.enemy.feature.track.playlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.buggily.enemy.core.ui.ui.playlist.PlaylistItem
import com.buggily.enemy.data.playlist.Playlist
import com.buggily.enemy.feature.track.R
import com.buggily.enemy.core.ui.R as CR

@Composable
fun TrackPlaylistPickerDialog(
    viewModel: TrackPlaylistPickerViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState: TrackPlaylistPickerUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val playlists: LazyPagingItems<Playlist> = viewModel.playlists.collectAsLazyPagingItems()

    TrackPlaylistPickerDialog(
        uiState = uiState,
        playlists = playlists,
        modifier = modifier,
    )
}

@Composable
private fun TrackPlaylistPickerDialog(
    uiState: TrackPlaylistPickerUiState,
    playlists: LazyPagingItems<Playlist>,
    modifier: Modifier = Modifier,
) {
    TrackPlaylistPickerDialog(
        playlistState = uiState.playlistState,
        playlists = playlists,
        modifier = modifier,
    )
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun TrackPlaylistPickerDialog(
    playlistState: TrackPlaylistPickerUiState.PlaylistState,
    playlists: LazyPagingItems<Playlist>,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = MaterialTheme.shapes.large,
        modifier = modifier,
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth(),
        ) {
            stickyHeader {
                TrackPlaylistPickerTitleText(
                    modifier = Modifier.padding(dimensionResource(CR.dimen.padding_large))
                )
            }

            items(
                count = playlists.itemCount,
                key = playlists.itemKey { it.id },
            ) {
                when (val playlist: Playlist? = playlists[it]) {
                    is Playlist -> PlaylistItem(
                        playlist = playlist,
                        onClick = { playlistState.onClick(playlist) },
                    )

                    else -> Unit
                }
            }
        }
    }
}

@Composable
private fun TrackPlaylistPickerTitleText(
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(R.string.track_add_playlist),
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier,
    )
}
