package com.buggily.enemy.ui.browse

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.buggily.enemy.R
import com.buggily.enemy.albums.AlbumsScreen
import com.buggily.enemy.albums.AlbumsState
import com.buggily.enemy.albums.AlbumsViewModel
import com.buggily.enemy.core.ui.IconButton
import com.buggily.enemy.core.ui.IconFloatingActionButton
import com.buggily.enemy.core.ui.SingleLineText
import com.buggily.enemy.core.ui.SingleLineTextField
import com.buggily.enemy.tracks.TracksScreen
import com.buggily.enemy.tracks.TracksState
import com.buggily.enemy.tracks.TracksViewModel
import kotlinx.coroutines.flow.collectLatest
import com.buggily.enemy.core.ui.R.dimen as dimens
import com.buggily.enemy.core.ui.R.drawable as drawables
import com.buggily.enemy.core.ui.R.string as strings

@Composable
@OptIn(ExperimentalLifecycleComposeApi::class)
fun BrowseScreen(
    viewModel: BrowseViewModel,
    albumState: AlbumsState.AlbumState,
    trackState: TracksState.TrackState,
    modifier: Modifier = Modifier,
) {
    val state: BrowseState by viewModel.state.collectAsStateWithLifecycle()

    BrowseScreen(
        state = state,
        albumState = albumState,
        trackState = trackState,
        modifier = modifier,
    )
}

@Composable
private fun BrowseScreen(
    state: BrowseState,
    albumState: AlbumsState.AlbumState,
    trackState: TracksState.TrackState,
    modifier: Modifier = Modifier,
) {
    BrowseScreen(
        tabState = state.tabState,
        searchState = state.searchState,
        albumState = albumState,
        trackState = trackState,
        modifier = modifier,
    )
}

@Composable
private fun BrowseScreen(
    tabState: BrowseState.TabState,
    searchState: BrowseState.SearchState,
    albumState: AlbumsState.AlbumState,
    trackState: TracksState.TrackState,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
        ) {
            BrowseTopAppBar(
                searchState = searchState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = dimensionResource(dimens.padding_large),
                        vertical = dimensionResource(dimens.padding_small),
                    ),
            )

            BrowseTabs(
                tabState = tabState,
                modifier = Modifier.fillMaxWidth(),
            )

            BrowseContent(
                tabState = tabState,
                albumState = albumState,
                trackState = trackState,
                modifier = Modifier.weight(1f),
            )
        }

        BrowseSearchFloatingActionButton(
            searchState = searchState,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .safeContentPadding()
                .padding(dimensionResource(dimens.padding_large)),
        )
    }
}

@Composable
private fun BrowseTopAppBar(
    searchState: BrowseState.SearchState,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = searchState.isVisible,
        enter = fadeIn() + expandVertically(expandFrom = Alignment.Top),
        exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Top),
        modifier = modifier,
    ) {
        BrowseSearch(
            searchState = searchState,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
private fun BrowseSearch(
    searchState: BrowseState.SearchState,
    modifier: Modifier = Modifier,
) {
    SingleLineTextField(
        value = searchState.value,
        onValueChange = searchState.onChange,
        modifier = modifier,
        label = { BrowseSearchLabel() },
        placeholder = { BrowseSearchPlaceholder() },
        trailingIcon = { BrowseSearchTrailingIcon(searchState) },
    )
}

@Composable
private fun BrowseSearchLabel(
    modifier: Modifier = Modifier,
) {
    SingleLineText(
        text = stringResource(strings.search),
        modifier = modifier,
    )
}

@Composable
private fun BrowseSearchPlaceholder(
    modifier: Modifier = Modifier,
) {
    SingleLineText(
        text = stringResource(strings.search),
        modifier = modifier,
    )
}

@Composable
private fun BrowseSearchTrailingIcon(
    searchState: BrowseState.SearchState,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = searchState.isClearVisible,
        enter = fadeIn() + expandIn(),
        exit = shrinkOut() + fadeOut(),
        modifier = modifier,
    ) {
        IconButton(
            painter = painterResource(drawables.clear),
            contentDescription = stringResource(strings.clear),
            onClick = searchState.onClear,
            contentModifier = Modifier.size(dimensionResource(dimens.icon_medium)),
        )
    }
}

@Composable
private fun BrowseTabs(
    tabState: BrowseState.TabState,
    modifier: Modifier = Modifier,
) {
    TabRow(
        selectedTabIndex = tabState.index,
        modifier = modifier,
    ) {
        tabState.tabs.forEach {
            val isSelected: Boolean = it == tabState.tab

            val stringResId: Int = when (it) {
                is BrowseState.TabState.Tab.Albums -> R.string.albums
                is BrowseState.TabState.Tab.Tracks -> R.string.tracks
            }

            Tab(
                selected = isSelected,
                onClick = { tabState.onClick(it) },
                text = {
                    Text(
                        text = stringResource(stringResId),
                        modifier = Modifier,
                    )
                },
            )
        }
    }
}

@Composable
private fun BrowseContent(
    tabState: BrowseState.TabState,
    albumState: AlbumsState.AlbumState,
    trackState: TracksState.TrackState,
    modifier: Modifier = Modifier,
) {
    when (tabState.tab) {
        is BrowseState.TabState.Tab.Albums -> BrowseAlbumsContent(
            albumState = albumState,
            modifier = modifier,
        )
        is BrowseState.TabState.Tab.Tracks -> BrowseTracksContent(
            trackState = trackState,
            modifier = modifier,
        )
    }
}

@Composable
private fun BrowseAlbumsContent(
    albumState: AlbumsState.AlbumState,
    modifier: Modifier = Modifier,
) {
    val viewModel: BrowseViewModel = hiltViewModel()
    val albumsViewModel: AlbumsViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.search.collectLatest {
            albumsViewModel.setSearch(it)
        }
    }

    AlbumsScreen(
        viewModel = albumsViewModel,
        albumState = albumState,
        modifier = modifier,
    )
}

@Composable
private fun BrowseTracksContent(
    trackState: TracksState.TrackState,
    modifier: Modifier = Modifier,
) {
    val viewModel: BrowseViewModel = hiltViewModel()
    val tracksViewModel: TracksViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.search.collectLatest {
            tracksViewModel.setSearch(it)
        }
    }

    TracksScreen(
        viewModel = tracksViewModel,
        trackState = trackState,
        modifier = modifier,
    )
}

@Composable
private fun BrowseSearchFloatingActionButton(
    searchState: BrowseState.SearchState,
    modifier: Modifier = Modifier,
) {
    val painterResId: Int = if (searchState.isVisible) {
        drawables.search_disable
    } else {
        drawables.search_enable
    }

    val stringResId: Int = if (searchState.isVisible) {
        strings.search_disable
    } else {
        strings.search_disable
    }

    IconFloatingActionButton(
        painter = painterResource(painterResId),
        contentDescription = stringResource(stringResId),
        onClick = searchState.onToggleVisibility,
        modifier = modifier,
        contentModifier = Modifier.size(dimensionResource(dimens.icon_medium)),
    )
}
