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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.buggily.enemy.R
import com.buggily.enemy.ui.EnemyDestination
import com.buggily.enemy.ui.albums.AlbumsScreen
import com.buggily.enemy.ui.ext.Gradient
import com.buggily.enemy.ui.ext.IconButton
import com.buggily.enemy.ui.main.MainViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel,
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier,
) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val lifecycle: Lifecycle = lifecycleOwner.lifecycle

    val state: HomeState by viewModel.state.collectAsStateWithLifecycle()
    val searchState: HomeState.SearchState = state.searchState
    val itSettingsState: HomeState.SettingsState = state.settingsState

    val onSettingsClick: () -> Unit = {
        navController.navigate(EnemyDestination.Settings.route) {
            launchSingleTop = true
            restoreState = false
        }
    }

    val settingsState: HomeState.SettingsState = remember(itSettingsState) {
        itSettingsState.copy(onSettingsClick = onSettingsClick)
    }

    LaunchedEffect(Unit) {
        mainViewModel.isSearch.flowWithLifecycle(lifecycle).collectLatest {
            searchState.onIsSearchChange(it)
        }
    }

    HomeScreen(
        searchState = searchState,
        settingsState = settingsState,
        modifier = modifier,
    ) {
        HomeScreenContent(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
        )
    }
}

@Composable
private fun HomeScreen(
    searchState: HomeState.SearchState,
    settingsState: HomeState.SettingsState,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        HomeSearchBar(
            searchState = searchState,
            settingsState = settingsState,
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
    searchState: HomeState.SearchState,
    settingsState: HomeState.SettingsState,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = searchState.isSearch,
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
                value = searchState.search,
                onValueChange = searchState.onSearchChange,
                singleLine = true,
                label = { HomeSearchBarLabel() },
                trailingIcon = { HomeSearchBarTrailingIcon(searchState) },
                modifier = Modifier.weight(1f),
            )

            HomeSettingsButton(
                settingsState = settingsState,
            )
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
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Box(modifier) {
        AlbumsScreen(
            navController = navController,
            homeViewModel = hiltViewModel(),
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
