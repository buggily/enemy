package com.buggily.enemy.albums

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.buggily.enemy.core.model.TimeOfDay
import com.buggily.enemy.core.model.album.Album
import com.buggily.enemy.core.ui.ArtImage
import com.buggily.enemy.core.ui.GreetingText
import com.buggily.enemy.core.ui.IconButton
import com.buggily.enemy.core.ui.ext.artistText
import com.buggily.enemy.core.ui.ext.items
import com.buggily.enemy.core.ui.ext.nameText
import com.buggily.enemy.core.ui.theme.ContentAlpha
import com.buggily.enemy.core.ui.R.dimen as dimens
import com.buggily.enemy.core.ui.R.drawable as drawables
import com.buggily.enemy.core.ui.R.string as strings

@Composable
@OptIn(ExperimentalLifecycleComposeApi::class)
fun AlbumsScreen(
    viewModel: AlbumsViewModel,
    albumState: AlbumsState.AlbumState,
    preferencesState: AlbumsState.PreferencesState,
    modifier: Modifier = Modifier,
) {
    val state: AlbumsState by viewModel.state.collectAsStateWithLifecycle()
    val albums: LazyPagingItems<Album> = viewModel.albums.collectAsLazyPagingItems()

    AlbumsScreen(
        state = state,
        albums = albums,
        albumState = albumState,
        preferencesState = preferencesState,
        modifier = modifier,
    )
}

@Composable
private fun AlbumsScreen(
    state: AlbumsState,
    albums: LazyPagingItems<Album>,
    albumState: AlbumsState.AlbumState,
    preferencesState: AlbumsState.PreferencesState,
    modifier: Modifier = Modifier,
) {
    AlbumsAlbumGrid(
        state = state,
        albums = albums,
        albumState = albumState,
        preferencesState = preferencesState,
        modifier = modifier,
    )
}

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalLayoutApi::class,
)

@Composable
private fun AlbumsAlbumGrid(
    state: AlbumsState,
    albums: LazyPagingItems<Album>,
    albumState: AlbumsState.AlbumState,
    preferencesState: AlbumsState.PreferencesState,
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
        )
            .add(WindowInsets.systemBars)
            .asPaddingValues(),
        modifier = modifier.consumedWindowInsets(WindowInsets.systemBars),
    ) {
        item(
            key = null,
            span = { GridItemSpan(maxCurrentLineSpan) },
        ) {
            AlbumHeader(
                timeOfDay = state.timeOfDay,
                preferencesState = preferencesState,
                modifier = Modifier.fillMaxWidth(),
            )
        }

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
private fun AlbumHeader(
    timeOfDay: TimeOfDay?,
    preferencesState: AlbumsState.PreferencesState,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = dimensionResource(dimens.padding_large),
            alignment = Alignment.Start,
        ),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        GreetingText(
            timeOfDay = timeOfDay,
            modifier = Modifier.weight(1f),
        )

        AlbumHeaderPreferencesIconButton(
            preferencesState = preferencesState,
        )
    }
}

@Composable
private fun AlbumHeaderPreferencesIconButton(
    preferencesState: AlbumsState.PreferencesState,
    modifier: Modifier = Modifier,
) {
    IconButton(
        painter = painterResource(drawables.preferences),
        contentDescription = stringResource(strings.preferences),
        onClick = preferencesState.onPreferencesClick,
        modifier = modifier,
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
