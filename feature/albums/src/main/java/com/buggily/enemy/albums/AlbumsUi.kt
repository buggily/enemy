package com.buggily.enemy.albums

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.buggily.enemy.core.ui.composable.ArtImage
import com.buggily.enemy.core.ui.ext.artistText
import com.buggily.enemy.core.ui.ext.floatResource
import com.buggily.enemy.core.ui.ext.items
import com.buggily.enemy.core.ui.ext.nameText
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
            items = albums,
            key = { it.id },
        ) {
            when (it) {
                is Album -> AlbumItem(
                    album = it,
                    onClick = { albumState.onClick(it) },
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun LazyGridItemScope.AlbumItem(
    album: Album,
    onClick: () -> Unit,
) {
    AlbumItem(
        album = album,
        modifier = Modifier
            .defaultMinSize(
                minWidth = dimensionResource(dimens.item),
                minHeight = dimensionResource(dimens.item),
            )
            .fillMaxSize()
            .aspectRatio(1f)
            .animateItemPlacement()
            .clickable { onClick() },
    )
}

@Composable
private fun AlbumItem(
    album: Album,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
    ) {
        AlbumItemImage(
            album = album,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun AlbumItemImage(
    album: Album,
    modifier: Modifier = Modifier,
) {
    ArtImage(
        artable = album,
        contentScale = ContentScale.Crop,
        modifier = modifier,
        error = {
            AlbumItemImageError(
                album = album,
                modifier = Modifier.defaultMinSize(
                    minWidth = dimensionResource(dimens.item),
                    minHeight = dimensionResource(dimens.item),
                ),
            )
        },
    )
}

@Composable
private fun AlbumItemImageError(
    album: Album,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(dimens.padding_small),
            alignment = Alignment.CenterVertically,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(
            text = album.nameText,
            style = MaterialTheme.typography.titleMedium,
        )

        Text(
            text = album.artistText,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.alpha(floatResource(dimens.alpha_medium)),
        )
    }
}
