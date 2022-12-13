package com.buggily.enemy.feature.album

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.buggily.enemy.core.model.album.Album
import com.buggily.enemy.core.ui.ArtImage
import com.buggily.enemy.core.ui.ext.items
import com.buggily.enemy.core.ui.theme.ContentAlpha
import com.buggily.enemy.core.ui.R.dimen as dimens

@Composable
fun AlbumsScreen(
    albumState: AlbumsState.AlbumState,
    viewModel: AlbumsViewModel,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    val albums: LazyPagingItems<Album> = viewModel.albums.collectAsLazyPagingItems()

    AlbumsScreen(
        albums = albums,
        albumState = albumState,
        contentPadding = contentPadding,
        modifier = modifier,
    )
}

@Composable
private fun AlbumsScreen(
    albums: LazyPagingItems<Album>,
    albumState: AlbumsState.AlbumState,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    AlbumsAlbumGrid(
        albums = albums,
        albumState = albumState,
        contentPadding = contentPadding,
        modifier = modifier,
    )
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun AlbumsAlbumGrid(
    albums: LazyPagingItems<Album>,
    albumState: AlbumsState.AlbumState,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(dimensionResource(dimens.item)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(dimens.padding_large)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(dimens.padding_large)),
        contentPadding = contentPadding,
        modifier = modifier,
    ) {
        items(
            items = albums,
            key = { it.id },
        ) {
            when (it) {
                is Album -> AlbumItem(
                    album = it,
                    modifier = Modifier
                        .fillMaxSize()
                        .defaultMinSize(
                            minWidth = dimensionResource(dimens.item),
                            minHeight = dimensionResource(dimens.item),
                        )
                        .aspectRatio(1f)
                        .animateItemPlacement()
                        .clickable { albumState.onAlbumClick(it) },
                )
            }
        }
    }
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
    val contentModifier: Modifier = Modifier.defaultMinSize(
        minWidth = dimensionResource(dimens.item),
        minHeight = dimensionResource(dimens.item),
    )

    ArtImage(
        artable = album,
        contentScale = ContentScale.Crop,
        modifier = modifier,
        error = {
            AlbumItemImageError(
                album = album,
                modifier = contentModifier,
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
            text = album.name,
            style = MaterialTheme.typography.titleMedium,
        )

        Text(
            text = album.artist.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.alpha(ContentAlpha.medium),
        )
    }
}
