package com.buggily.enemy.ui.browse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.buggily.enemy.R
import com.buggily.enemy.albums.AlbumsScreen
import com.buggily.enemy.albums.AlbumsState
import com.buggily.enemy.albums.AlbumsViewModel
import com.buggily.enemy.tracks.TracksScreen
import com.buggily.enemy.tracks.TracksState
import com.buggily.enemy.tracks.TracksViewModel
import kotlinx.coroutines.flow.collectLatest

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
                is BrowseState.TabState.Tab.Playlists -> R.string.playlists
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
        else -> Unit
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
