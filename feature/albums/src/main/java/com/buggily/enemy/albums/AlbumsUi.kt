package com.buggily.enemy.albums

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
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
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.buggily.enemy.core.model.album.Album
import com.buggily.enemy.core.ui.ArtImage
import com.buggily.enemy.core.ui.ext.artistText
import com.buggily.enemy.core.ui.ext.items
import com.buggily.enemy.core.ui.ext.nameText
import com.buggily.enemy.core.ui.theme.ContentAlpha
import com.buggily.enemy.core.ui.R.dimen as dimens

@Composable
@OptIn(ExperimentalLifecycleComposeApi::class)
fun AlbumsScreen(
    viewModel: AlbumsViewModel,
    albumState: AlbumsState.AlbumState,
    modifier: Modifier = Modifier,
) {
    val albums: LazyPagingItems<Album> = viewModel.albums.collectAsLazyPagingItems()

    AlbumsScreen(
        albums = albums,
        albumState = albumState,
        modifier = modifier,
    )
}

@Composable
private fun AlbumsScreen(
    albums: LazyPagingItems<Album>,
    albumState: AlbumsState.AlbumState,
    modifier: Modifier = Modifier,
) {
    AlbumsAlbumGrid(
        albums = albums,
        albumState = albumState,
        modifier = modifier,
    )
}

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalLayoutApi::class,
)

@Composable
private fun AlbumsAlbumGrid(
    albums: LazyPagingItems<Album>,
    albumState: AlbumsState.AlbumState,
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
                    modifier = Modifier
                        .defaultMinSize(
                            minWidth = dimensionResource(dimens.item),
                            minHeight = dimensionResource(dimens.item),
                        )
                        .fillMaxSize()
                        .aspectRatio(1f)
                        .animateItemPlacement()
                        .clickable { albumState.onClick(it) },
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
            text = album.nameText,
            style = MaterialTheme.typography.titleMedium,
        )

        Text(
            text = album.artistText,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.alpha(ContentAlpha.medium),
        )
    }
}
