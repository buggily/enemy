package com.buggily.enemy.ui.albums

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.buggily.enemy.R
import com.buggily.enemy.domain.album.Album
import com.buggily.enemy.ext.nullableItems
import com.buggily.enemy.ui.ContentAlpha
import com.buggily.enemy.ui.EnemyDestination
import com.buggily.enemy.ui.ext.ArtImage
import com.buggily.enemy.ui.home.HomeState
import com.buggily.enemy.ui.home.HomeViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AlbumsScreen(
    navController: NavController,
    viewModel: AlbumsViewModel,
    homeViewModel: HomeViewModel,
    modifier: Modifier = Modifier,
) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val lifecycle: Lifecycle = lifecycleOwner.lifecycle

    val albums: LazyPagingItems<Result<Album>> = viewModel.albums.collectAsLazyPagingItems()
    val state: AlbumsState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        homeViewModel.state.flowWithLifecycle(lifecycle).collectLatest {
            val itSearchState: HomeState.SearchState = it.searchState
            val itSearch: String = itSearchState.search

            state.searchState.onSearchChange(itSearch)
        }
    }

    AlbumsScreen(
        navController = navController,
        albums = albums,
        modifier = modifier,
    )
}

@Composable
private fun AlbumsScreen(
    navController: NavController,
    albums: LazyPagingItems<Result<Album>>,
    modifier: Modifier = Modifier,
) {
    val layoutDirection: LayoutDirection = LocalLayoutDirection.current
    val padding: Dp = dimensionResource(R.dimen.padding_large)
    val navigationBarsPadding: PaddingValues = WindowInsets.navigationBars.asPaddingValues()

    val startPadding: Dp = padding + navigationBarsPadding.calculateStartPadding(layoutDirection)
    val topPadding: Dp = padding + navigationBarsPadding.calculateTopPadding()
    val endPadding: Dp = padding + navigationBarsPadding.calculateEndPadding(layoutDirection)
    val bottomPadding: Dp = padding + navigationBarsPadding.calculateBottomPadding()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(dimensionResource(R.dimen.item)),
        contentPadding = PaddingValues(
            start = startPadding,
            top = topPadding,
            end = endPadding,
            bottom = bottomPadding,
        ),
        verticalArrangement = Arrangement.spacedBy(padding),
        horizontalArrangement = Arrangement.spacedBy(padding),
        modifier = modifier,
    ) {
        val contentModifier: Modifier = Modifier.fillMaxSize()

        nullableItems(
            items = albums,
            key = { it.getOrNull()?.id },
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = contentModifier,
            ) {
                when (val album: Album? = remember(it) { it?.getOrNull() }) {
                    is Album -> AlbumItem(
                        album = album,
                        modifier = contentModifier.clickable {
                            navController.navigate(EnemyDestination.Album.getRoute(album.id)) {
                                launchSingleTop = true
                                restoreState = false
                            }
                        },
                    )
                    else -> Unit
                }
            }
        }
    }
}

@Composable
private fun AlbumItem(
    album: Album,
    modifier: Modifier = Modifier,
) {
    AlbumItemImage(
        album = album,
        modifier = modifier,
    )
}

@Composable
private fun AlbumItemImage(
    album: Album,
    modifier: Modifier = Modifier,
) {
    val composeModifier: Modifier = Modifier.defaultMinSize(
        minWidth = dimensionResource(R.dimen.item),
        minHeight = dimensionResource(R.dimen.item),
    )

    ArtImage(
        artable = album,
        contentScale = ContentScale.Fit,
        modifier = modifier,
        error = {
            AlbumItemImageError(
                album = album,
                modifier = composeModifier,
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
            space = dimensionResource(R.dimen.padding_small),
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
            modifier = Modifier.alpha(ContentAlpha.MEDIUM),
        )
    }
}
