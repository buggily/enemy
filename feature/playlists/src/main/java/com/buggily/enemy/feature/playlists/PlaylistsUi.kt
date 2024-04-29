package com.buggily.enemy.feature.playlists

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
import com.buggily.enemy.core.ui.ui.playlist.PlaylistItem
import com.buggily.enemy.domain.playlist.PlaylistUi

@Composable
fun PlaylistsScreen(
    viewModel: PlaylistsViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState: PlaylistsUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val playlists: LazyPagingItems<PlaylistUi> = viewModel.playlists.collectAsLazyPagingItems()

    Box(modifier) {
        PlaylistsScreen(
            uiState = uiState,
            playlists = playlists,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun PlaylistsScreen(
    uiState: PlaylistsUiState,
    playlists: LazyPagingItems<PlaylistUi>,
    modifier: Modifier = Modifier,
) {
    PlaylistsScreen(
        playlistState = uiState.playlistState,
        playlists = playlists,
        modifier = modifier,
    )
}

@Composable
private fun PlaylistsScreen(
    playlistState: PlaylistsUiState.PlaylistState,
    playlists: LazyPagingItems<PlaylistUi>,
    modifier: Modifier = Modifier,
) {
    PlaylistsColumn(
        playlistState = playlistState,
        playlists = playlists,
        modifier = modifier,
    )
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun PlaylistsColumn(
    playlistState: PlaylistsUiState.PlaylistState,
    playlists: LazyPagingItems<PlaylistUi>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        stickyHeader { PlaylistsHeader(Modifier.fillMaxWidth()) }

        items(
            count = playlists.itemCount,
            key = playlists.itemKey { it.id },
        ) {
            when (val playlist: PlaylistUi? = playlists[it]) {
                is PlaylistUi -> PlaylistItem(
                    playlist = playlist,
                    onClick = { playlistState.onClick(playlist) },
                    onLongClick = { playlistState.onLongClick(playlist) },
                )

                else -> Unit
            }
        }
    }
}

@Composable
private fun PlaylistsHeader(
    modifier: Modifier = Modifier,
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier,
    ) {
        PlaylistItem(
            nameText = stringResource(R.string.playlists_name),
            modificationText = stringResource(R.string.playlists_modification_date),
        )
    }
}
