package com.buggily.enemy.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.buggily.enemy.feature.album.albums.AlbumsScreen
import com.buggily.enemy.feature.album.albums.AlbumsState

@Composable
fun HomeScreen(
    albumState: AlbumsState.AlbumState,
    viewModel: HomeViewModel,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    HomeScreen(
        albumState = albumState,
        contentPadding = contentPadding,
        modifier = modifier,
    )
}

@Composable
private fun HomeScreen(
    albumState: AlbumsState.AlbumState,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    AlbumsScreen(
        albumState = albumState,
        viewModel = hiltViewModel(),
        contentPadding = contentPadding,
        modifier = modifier,
    )
}
