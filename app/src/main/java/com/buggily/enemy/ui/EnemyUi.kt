package com.buggily.enemy.ui

import android.content.pm.PackageManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.buggily.enemy.albums.AlbumsState
import com.buggily.enemy.controller.ControllerBottomSheet
import com.buggily.enemy.controller.ControllerScreen
import com.buggily.enemy.controller.ControllerState
import com.buggily.enemy.core.ext.readPermission
import com.buggily.enemy.feature.album.AlbumScreen
import com.buggily.enemy.feature.album.AlbumState
import com.buggily.enemy.feature.orientation.OrientationScreen
import com.buggily.enemy.feature.orientation.OrientationState
import com.buggily.enemy.feature.preferences.PreferencesScreen
import com.buggily.enemy.navigation.EnemyDestination
import com.buggily.enemy.tracks.TracksState
import com.buggily.enemy.ui.browse.BrowseScreen

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
        tracksTrackState = state.tracksTrackState,
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
    tracksTrackState: TracksState.TrackState,
    modifier: Modifier = Modifier,
) {
    val isControllerVisible: Boolean = appState.isControllerVisible && controllerState.isVisible

    val onControllerClick: () -> Unit = {
        appState.navigate(EnemyDestination.Controller.route) {
            launchSingleTop = true
            restoreState = false
        }
    }

    val onNotGranted: () -> Unit = {
        appState.navigate(EnemyDestination.Orientation.route) {
            launchSingleTop = true
            restoreState = false

            popUpTo(EnemyDestination.startDestination.route) {
                inclusive = true
                saveState = false
            }
        }
    }

    val orientationAlbumsState = OrientationState.AlbumsState {
        appState.navigate(EnemyDestination.startDestination.route) {
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

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = isControllerVisible,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically(),
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
        val permissionResult: Int = ContextCompat.checkSelfPermission(
            LocalContext.current,
            readPermission
        )

        LaunchedEffect(Unit) {
            val isGranted: Boolean = permissionResult == PackageManager.PERMISSION_GRANTED
            if (!isGranted) onNotGranted()
        }

        NavHost(
            navController = appState.navController,
            startDestination = EnemyDestination.startDestination.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .consumedWindowInsets(padding)
                .animateContentSize(),
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
                route = EnemyDestination.Browse.route,
                arguments = EnemyDestination.Browse.arguments,
            ) {
                BrowseScreen(
                    viewModel = hiltViewModel(),
                    albumState = albumsAlbumState,
                    trackState = tracksTrackState,
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
