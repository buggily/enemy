package com.buggily.enemy.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.buggily.enemy.R
import com.buggily.enemy.domain.search.Search
import com.buggily.enemy.ui.albums.AlbumsScreen
import com.buggily.enemy.ui.albums.AlbumsState
import com.buggily.enemy.ui.ext.Gradient
import com.buggily.enemy.ui.ext.IconButton

@Composable
fun HomeScreen(
    albumState: AlbumsState.AlbumState,
    settingsState: HomeState.SettingsState,
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
) {
    val state: HomeState by viewModel.state.collectAsStateWithLifecycle()
    val searchState: HomeState.SearchState = state.searchState

    HomeScreen(
        settingsState = settingsState,
        searchState = searchState,
        modifier = modifier,
    ) {
        HomeScreenContent(
            albumState = albumState,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
        )
    }
}

@Composable
private fun HomeScreen(
    settingsState: HomeState.SettingsState,
    searchState: HomeState.SearchState,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        HomeSearchBar(
            settingsState = settingsState,
            searchState = searchState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(R.dimen.padding_large),
                    vertical = dimensionResource(R.dimen.padding_medium),
                ),
        )

        content()
    }
}

@Composable
private fun HomeSearchBar(
    settingsState: HomeState.SettingsState,
    searchState: HomeState.SearchState,
    modifier: Modifier = Modifier,
) {
    val search: Search = searchState.search
    val isSearch: Boolean = search.isVisible

    AnimatedVisibility(
        visible = isSearch,
        enter = expandVertically(
            expandFrom = Alignment.Bottom,
        ),
        exit = shrinkVertically(
            shrinkTowards = Alignment.Top,
        ),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = dimensionResource(R.dimen.padding_medium),
                alignment = Alignment.Start,
            ),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier,
        ) {
            OutlinedTextField(
                value = search.value,
                onValueChange = searchState.onSearchChange,
                singleLine = true,
                label = { HomeSearchBarLabel() },
                trailingIcon = { HomeSearchBarTrailingIcon(searchState) },
                modifier = Modifier.weight(1f),
            )

            HomeSettingsButton(settingsState)
        }
    }
}

@Composable
private fun HomeSettingsButton(
    settingsState: HomeState.SettingsState,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = settingsState.onSettingsClick,
        painter = painterResource(R.drawable.settings),
        contentDescription = stringResource(R.string.settings),
        modifier = modifier,
    )
}

@Composable
private fun HomeSearchBarLabel(
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(R.string.search),
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier,
    )
}

@Composable
private fun HomeSearchBarTrailingIcon(
    searchState: HomeState.SearchState,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = searchState.isSearchClear,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        IconButton(
            onClick = searchState.onSearchClearClick,
            painter = painterResource(R.drawable.clear),
            contentDescription = stringResource(R.string.clear),
            modifier = modifier,
        )
    }
}

@Composable
private fun HomeScreenContent(
    albumState: AlbumsState.AlbumState,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        AlbumsScreen(
            albumState = albumState,
            viewModel = hiltViewModel(),
            modifier = Modifier.fillMaxSize(),
        )

        Gradient(
            orientation = Orientation.Vertical,
            colors = listOf(
                MaterialTheme.colorScheme.background,
                Color.Transparent,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.padding_larger))
                .align(Alignment.TopCenter),
        )
    }
}
