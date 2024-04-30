package com.buggily.enemy.feature.browse

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.buggily.enemy.albums.AlbumsScreen
import com.buggily.enemy.albums.AlbumsViewModel
import com.buggily.enemy.core.ui.GlobalUiViewModel
import com.buggily.enemy.core.ui.SearchableViewModel
import com.buggily.enemy.feature.playlists.PlaylistsScreen
import com.buggily.enemy.feature.playlists.PlaylistsViewModel
import com.buggily.enemy.feature.recent.RecentScreen
import com.buggily.enemy.feature.recent.RecentViewModel
import com.buggily.enemy.tracks.TracksScreen
import com.buggily.enemy.tracks.TracksViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun BrowseScreen(
    viewModel: BrowseViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState: BrowseUiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier) {
        BrowseScreen(
            uiState = uiState,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun BrowseScreen(
    uiState: BrowseUiState,
    modifier: Modifier = Modifier,
) {
    BrowseScreen(
        tabState = uiState.tabState,
        modifier = modifier,
    )
}

@Composable
private fun BrowseScreen(
    tabState: BrowseUiState.TabState,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxSize(),
        ) {
            BrowseTabs(
                tabState = tabState,
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding(),
            )

            BrowseContent(
                tabState = tabState,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun BrowseTabs(
    tabState: BrowseUiState.TabState,
    modifier: Modifier = Modifier,
) {
    ScrollableTabRow(
        selectedTabIndex = tabState.index,
        modifier = modifier,
    ) {
        tabState.tabs.forEach {
            val isSelected: Boolean = it == tabState.tab

            Tab(
                selected = isSelected,
                text = {
                    Text(
                        text = stringResource(it.stringResId),
                        modifier = Modifier,
                    )
                },
                onClick = { tabState.onClick(it) },
            )
        }
    }
}

@Composable
private fun BrowseContent(
    tabState: BrowseUiState.TabState,
    modifier: Modifier = Modifier,
) {
    val context: Context = LocalContext.current
    val activity: ComponentActivity = context as ComponentActivity
    val globalUiViewModel: GlobalUiViewModel = hiltViewModel(activity)

    Box(modifier) {
        val contentModifier: Modifier = Modifier.fillMaxSize()
        val tab: BrowseUiState.TabState.Tab = tabState.tab

        LaunchedEffect(tab) {
            globalUiViewModel.onIsSearchableChange(tab.isSearchable)
        }

        when (tab) {
            is BrowseUiState.TabState.Tab.Recent -> {
                val viewModel: RecentViewModel = hiltViewModel()

                RecentScreen(
                    viewModel = viewModel,
                    modifier = contentModifier,
                )
            }

            is BrowseUiState.TabState.Tab.Albums -> {
                val viewModel: AlbumsViewModel = hiltViewModel()

                BrowseSearchableContent(
                    viewModel = viewModel,
                    globalUiViewModel = globalUiViewModel,
                ) {
                    AlbumsScreen(
                        viewModel = viewModel,
                        modifier = contentModifier,
                    )
                }
            }

            is BrowseUiState.TabState.Tab.Tracks -> {
                val viewModel: TracksViewModel = hiltViewModel()

                BrowseSearchableContent(
                    viewModel = viewModel,
                    globalUiViewModel = globalUiViewModel,
                ) {
                    TracksScreen(
                        viewModel = viewModel,
                        modifier = contentModifier,
                    )
                }
            }

            is BrowseUiState.TabState.Tab.Playlists -> {
                val viewModel: PlaylistsViewModel = hiltViewModel()

                BrowseSearchableContent(
                    viewModel = viewModel,
                    globalUiViewModel = globalUiViewModel,
                ) {
                    PlaylistsScreen(
                        viewModel = viewModel,
                        modifier = contentModifier,
                    )
                }
            }
        }
    }
}

@Composable
private fun BrowseSearchableContent(
    viewModel: SearchableViewModel,
    globalUiViewModel: GlobalUiViewModel,
    content: @Composable () -> Unit,
) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val lifecycle: Lifecycle = lifecycleOwner.lifecycle

    LaunchedEffect(globalUiViewModel) {
        globalUiViewModel.search.flowWithLifecycle(lifecycle).collectLatest {
            viewModel.onSearchChange(it)
        }
    }

    content()
}
