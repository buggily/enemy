package com.buggily.enemy.albums

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
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
import com.buggily.enemy.domain.album.AlbumUi
import com.buggily.enemy.core.ui.R as CR

@Composable
fun AlbumsScreen(
    viewModel: AlbumsViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState: AlbumsUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val albums: LazyPagingItems<AlbumUi> = viewModel.albums.collectAsLazyPagingItems()

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
    albums: LazyPagingItems<AlbumUi>,
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
    albums: LazyPagingItems<AlbumUi>,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(dimensionResource(CR.dimen.item)),
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(CR.dimen.padding_large),
            alignment = Alignment.Top,
        ),
        horizontalArrangement = Arrangement.spacedBy(
            space = dimensionResource(CR.dimen.padding_large),
            alignment = Alignment.Start,
        ),
        contentPadding = WindowInsets(
            left = dimensionResource(CR.dimen.padding_large),
            top = dimensionResource(CR.dimen.padding_large),
            right = dimensionResource(CR.dimen.padding_large),
            bottom = dimensionResource(CR.dimen.padding_large),
        ).asPaddingValues(),
        modifier = modifier,
    ) {
        items(
            count = albums.itemCount,
            key = albums.itemKey { it.id },
        ) {
            when (val album: AlbumUi? = albums[it]) {
                is AlbumUi -> AlbumItem(
                    album = album,
                    modifier = Modifier
                        .defaultMinSize(
                            minWidth = dimensionResource(CR.dimen.item),
                            minHeight = dimensionResource(CR.dimen.item),
                        )
                        .clickable { albumState.onClick(album) }
                        .aspectRatio(1f),
                )

                else -> Unit
            }
        }
    }
}

