package com.buggily.enemy.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.buggily.enemy.EnemyAppState
import com.buggily.enemy.EnemyDestination
import com.buggily.enemy.EnemyState
import com.buggily.enemy.EnemyViewModel
import com.buggily.enemy.core.ui.R
import com.buggily.enemy.feature.album.AlbumScreen
import com.buggily.enemy.feature.album.AlbumState
import com.buggily.enemy.feature.album.albums.AlbumsScreen
import com.buggily.enemy.feature.album.albums.AlbumsState
import com.buggily.enemy.feature.orientation.OrientationScreen
import com.buggily.enemy.feature.orientation.OrientationState
import com.buggily.enemy.feature.preferences.PreferencesScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberEnemyAppState(
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    windowSizeClass: WindowSizeClass,
) : EnemyAppState = remember(
    navController,
    coroutineScope,
    windowSizeClass,
) {
    EnemyAppState(
        navController = navController,
        coroutineScope = coroutineScope,
        windowSizeClass = windowSizeClass,
    )
}

@Composable
@OptIn(ExperimentalLifecycleComposeApi::class)
fun EnemyApp(
    viewModel: EnemyViewModel,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
) {
    val appState: EnemyAppState = rememberEnemyAppState(
        navController = rememberNavController(),
        coroutineScope = rememberCoroutineScope(),
        windowSizeClass = windowSizeClass,
    )

    val hostState: SnackbarHostState = remember { SnackbarHostState() }
    val state: EnemyState by viewModel.state.collectAsStateWithLifecycle()

    EnemyApp(
        appState = appState,
        hostState = hostState,
        controllerState = state.controllerState,
        repeatState = state.repeatState,
        shuffleState = state.shuffleState,
        albumTrackState = state.albumTrackState,
        modifier = modifier,
    )
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class
)

@Composable
private fun EnemyApp(
    appState: EnemyAppState,
    hostState: SnackbarHostState,
    controllerState: EnemyState.ControllerState,
    repeatState: EnemyState.RepeatState,
    shuffleState: EnemyState.ShuffleState,
    albumTrackState: AlbumState.TrackState,
    modifier: Modifier = Modifier,
) {
    val controllerModifier: Modifier = if (controllerState.isExpanded) {
        Modifier.fillMaxHeight()
    } else {
        Modifier.height(IntrinsicSize.Min)
    }

    val contentInsets: WindowInsets = if (controllerState.isVisible) {
        WindowInsets(
            left = 0.dp,
            top = 0.dp,
            right = 0.dp,
            bottom = 0.dp,
        )
    } else {
        WindowInsets.navigationBars
    }

    Scaffold(
        bottomBar = {
            EnemyBottomBar(
                controllerState = controllerState,
                repeatState = repeatState,
                shuffleState = shuffleState,
                modifier = controllerModifier
                    .fillMaxWidth()
                    .clickable { controllerState.onClick() },
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = hostState,
            )
        },
        contentWindowInsets = WindowInsets.ime,
        modifier = modifier,
    ) { padding: PaddingValues ->
        val orientationAlbumsState = OrientationState.AlbumsState {
            appState.navController.navigate(EnemyDestination.Albums.route) {
                launchSingleTop = true
                restoreState = false

                popUpTo(EnemyDestination.Orientation.route) {
                    inclusive = true
                    saveState = false
                }
            }
        }

        val albumsAlbumState = AlbumsState.AlbumState {
            appState.navController.navigate(EnemyDestination.Album.getRoute(it.id)) {
                launchSingleTop = true
                restoreState = false
            }
        }

        NavHost(
            navController = appState.navController,
            startDestination = EnemyDestination.Orientation.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .consumedWindowInsets(padding)
                .consumedWindowInsets(WindowInsets.navigationBars),
        ) {
            val contentModifier: Modifier = Modifier.fillMaxSize()

            composable(
                route = EnemyDestination.Orientation.route,
                arguments = EnemyDestination.Orientation.arguments,
                deepLinks = EnemyDestination.Orientation.deepLinks,
            ) {
                OrientationScreen(
                    albumsState = orientationAlbumsState,
                    viewModel = hiltViewModel(),
                    modifier = contentModifier,
                )
            }

            composable(
                route = EnemyDestination.Albums.route,
                arguments = EnemyDestination.Albums.arguments,
                deepLinks = EnemyDestination.Albums.deepLinks,
            ) {
                AlbumsScreen(
                    albumState = albumsAlbumState,
                    viewModel = hiltViewModel(),
                    contentPadding = WindowInsets(
                        left = dimensionResource(R.dimen.padding_large),
                        top = dimensionResource(R.dimen.padding_large),
                        right = dimensionResource(R.dimen.padding_large),
                        bottom = dimensionResource(R.dimen.padding_large),
                    )
                        .add(WindowInsets.statusBars)
                        .add(contentInsets)
                        .asPaddingValues(),
                    modifier = contentModifier,
                )
            }

            composable(
                route = EnemyDestination.Album.route,
                arguments = EnemyDestination.Album.arguments,
                deepLinks = EnemyDestination.Album.deepLinks,
            ) {
                AlbumScreen(
                    viewModel = hiltViewModel(),
                    trackState = albumTrackState,
                    contentPadding = contentInsets.asPaddingValues(),
                    modifier = contentModifier,
                )
            }

            composable(
                route = EnemyDestination.Settings.route,
                arguments = EnemyDestination.Settings.arguments,
                deepLinks = EnemyDestination.Settings.deepLinks,
            ) {
                PreferencesScreen(
                    viewModel = hiltViewModel(),
                    modifier = contentModifier,
                )
            }
        }
    }
}
