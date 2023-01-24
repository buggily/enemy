package com.buggily.enemy.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
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
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.buggily.enemy.EnemyViewModel
import com.buggily.enemy.albums.AlbumsScreen
import com.buggily.enemy.albums.AlbumsState
import com.buggily.enemy.controller.ControllerBottomSheet
import com.buggily.enemy.controller.ControllerScreen
import com.buggily.enemy.controller.ControllerState
import com.buggily.enemy.feature.album.AlbumScreen
import com.buggily.enemy.feature.album.AlbumState
import com.buggily.enemy.feature.orientation.OrientationScreen
import com.buggily.enemy.feature.orientation.OrientationState
import com.buggily.enemy.feature.preferences.PreferencesScreen
import com.buggily.enemy.navigation.EnemyDestination

@Composable
fun rememberEnemyAppState(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
): EnemyAppState = remember(
    navController,
    windowSizeClass,
) {
    EnemyAppState(
        navController = navController,
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
        windowSizeClass = windowSizeClass,
    )

    val hostState: SnackbarHostState = remember { SnackbarHostState() }
    val state: EnemyState by viewModel.state.collectAsStateWithLifecycle()

    EnemyApp(
        appState = appState,
        hostState = hostState,
        controllerState = state.controllerState,
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
    controllerState: ControllerState,
    albumTrackState: AlbumState.TrackState,
    modifier: Modifier = Modifier,
) {
    val isControllerVisible: Boolean = appState.isControllerVisible && controllerState.isVisible

    val onControllerClick: () -> Unit = {
        appState.navigate(EnemyDestination.Controller.route) {
            launchSingleTop = true
            restoreState = false
        }
    }

    val orientationAlbumsState = OrientationState.AlbumsState {
        appState.navigate(EnemyDestination.Albums.route) {
            launchSingleTop = true
            restoreState = false

            popUpTo(EnemyDestination.Orientation.route) {
                inclusive = true
                saveState = false
            }
        }
    }

    val albumsAlbumState = AlbumsState.AlbumState {
        appState.navigate(EnemyDestination.Album.getRoute(it.id)) {
            launchSingleTop = true
            restoreState = false
        }
    }

    val albumsPreferencesState = AlbumsState.PreferencesState {
        appState.navigate(EnemyDestination.Preferences.route) {
            launchSingleTop = true
            restoreState = false
        }
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = isControllerVisible,
                modifier = Modifier.fillMaxWidth(),
            ) {
                ControllerBottomSheet(
                    state = controllerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .clickable { onControllerClick() }
                        .consumedWindowInsets(WindowInsets.statusBars),
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState) },
        contentWindowInsets = WindowInsets.ime,
        modifier = modifier.consumedWindowInsets(WindowInsets.ime),
    ) { padding: PaddingValues ->
        NavHost(
            navController = appState.navController,
            startDestination = EnemyDestination.Orientation.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .consumedWindowInsets(padding),
        ) {
            val contentModifier: Modifier = Modifier.fillMaxSize()

            composable(
                route = EnemyDestination.Orientation.route,
                arguments = EnemyDestination.Orientation.arguments,
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
            ) {
                AlbumsScreen(
                    viewModel = hiltViewModel(),
                    albumState = albumsAlbumState,
                    preferencesState = albumsPreferencesState,
                    modifier = contentModifier,
                )
            }

            composable(
                route = EnemyDestination.Album.route,
                arguments = EnemyDestination.Album.arguments,
            ) {
                AlbumScreen(
                    viewModel = hiltViewModel(),
                    trackState = albumTrackState,
                    modifier = contentModifier,
                )
            }

            composable(
                route = EnemyDestination.Preferences.route,
                arguments = EnemyDestination.Preferences.arguments,
            ) {
                PreferencesScreen(
                    viewModel = hiltViewModel(),
                    modifier = contentModifier,
                )
            }

            composable(
                route = EnemyDestination.Controller.route,
                arguments = EnemyDestination.Controller.arguments,
            ) {
                ControllerScreen(
                    state = controllerState,
                    modifier = contentModifier,
                )
            }
        }
    }
}
