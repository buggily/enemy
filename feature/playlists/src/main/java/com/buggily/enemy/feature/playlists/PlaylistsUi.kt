package com.buggily.enemy.feature.playlists

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
import com.buggily.enemy.core.ui.ext.floatResource
import com.buggily.enemy.core.ui.ext.modificationText
import com.buggily.enemy.core.ui.ext.nameText
import com.buggily.enemy.data.playlist.Playlist
import com.buggily.enemy.core.ui.R.dimen as dimens

@Composable
fun PlaylistsScreen(
    viewModel: PlaylistsViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState: PlaylistsUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val playlists: LazyPagingItems<Playlist> = viewModel.playlists.collectAsLazyPagingItems()

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
    playlists: LazyPagingItems<Playlist>,
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
    playlists: LazyPagingItems<Playlist>,
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
    playlists: LazyPagingItems<Playlist>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        stickyHeader { PlaylistsHeader(Modifier.fillMaxWidth()) }

        items(playlists) {
            when (it) {
                is Playlist -> PlaylistItem(
                    playlist = it,
                    onClick = { playlistState.onClick(it) },
                )
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
            nameText = stringResource(R.string.name),
            modificationText = stringResource(R.string.modification_date),
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(dimens.padding_large)),
        )
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun LazyItemScope.PlaylistItem(
    playlist: Playlist,
    onClick: () -> Unit,
) {
    PlaylistItem(
        playlist = playlist,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = dimensionResource(dimens.padding_large_extra_extra))
            .clickable { onClick() }
            .animateItemPlacement()
            .padding(
                horizontal = dimensionResource(dimens.padding_large),
                vertical = dimensionResource(dimens.padding_large_extra),
            ),
    )
}

@Composable
private fun PlaylistItem(
    playlist: Playlist,
    modifier: Modifier = Modifier,
) {
    PlaylistItem(
        nameText = playlist.nameText,
        modificationText = playlist.modificationText,
        modifier = modifier,
    )
}

@Composable
private fun PlaylistItem(
    nameText: String,
    modificationText: String,
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
            modifier = Modifier.weight(1f),
        )

        Text(
            text = modificationText,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .weight(1f)
                .alpha(floatResource(dimens.alpha_medium)),
        )
    }
}
