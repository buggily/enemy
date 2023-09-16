package com.buggily.enemy.albums

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.buggily.enemy.core.ui.ui.album.AlbumItem
import com.buggily.enemy.data.album.Album
import com.buggily.enemy.core.ui.R.dimen as dimens

@Composable
fun AlbumsScreen(
    viewModel: AlbumsViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState: AlbumsUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val albums: LazyPagingItems<Album> = viewModel.albums.collectAsLazyPagingItems()

    Box(modifier) {
        AlbumsScreen(
            uiState = uiState,
            albums = albums,
            modifier = Modifier.fillMaxSize(),
        )
    }
}


@Composable
private fun AlbumsScreen(
    uiState: AlbumsUiState,
    albums: LazyPagingItems<Album>,
    modifier: Modifier = Modifier,
) {
    AlbumsGrid(
        albumState = uiState.albumState,
        albums = albums,
        modifier = modifier,
    )
}

@Composable
private fun AlbumsGrid(
    albumState: AlbumsUiState.AlbumState,
    albums: LazyPagingItems<Album>,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(dimensionResource(dimens.item)),
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(dimens.padding_large),
            alignment = Alignment.Top,
        ),
        horizontalArrangement = Arrangement.spacedBy(
            space = dimensionResource(dimens.padding_large),
            alignment = Alignment.Start,
        ),
        contentPadding = WindowInsets(
            left = dimensionResource(dimens.padding_large),
            top = dimensionResource(dimens.padding_large),
            right = dimensionResource(dimens.padding_large),
            bottom = dimensionResource(dimens.padding_large),
        ).asPaddingValues(),
        modifier = modifier,
    ) {
        items(
            count = albums.itemCount,
            key = albums.itemKey { it.id },
        ) {
            when (val album: Album? = albums[it]) {
                is Album -> AlbumItem(
                    album = album,
                    onClick = { albumState.onClick(album) },
                )

                else -> Unit
            }
        }
    }
}

