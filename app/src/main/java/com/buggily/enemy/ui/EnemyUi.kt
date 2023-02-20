package com.buggily.enemy.ui

import android.content.pm.PackageManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.buggily.enemy.core.ui.R.dimen as dimens
import com.buggily.enemy.core.ui.R.drawable as drawables
import com.buggily.enemy.core.ui.R.string as strings

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
        destinationState = state.destinationState,
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
    destinationState: EnemyState.DestinationState,
    controllerState: ControllerState,
    albumTrackState: AlbumState.TrackState,
    tracksTrackState: TracksState.TrackState,
    modifier: Modifier = Modifier,
) {
    val appControllerState = EnemyState.AppControllerState(
        isVisible = appState.isBottomBarVisible,
    ) {
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
                saveState = false
                inclusive = true
            }
        }
    }

    val orientationAlbumsState = OrientationState.AlbumsState {
        appState.navigate(EnemyDestination.startDestination.route) {
            launchSingleTop = true
            restoreState = false

            popUpTo(EnemyDestination.Orientation.route) {
                saveState = false
                inclusive = true
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
            EnemyBottomBar(
                appState = appState,
                destinationState = destinationState,
                appControllerState = appControllerState,
                controllerState = controllerState,
                modifier = Modifier.fillMaxWidth(),
            )
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
                route = EnemyDestination.Top.Browse.route,
                arguments = EnemyDestination.Top.Browse.arguments,
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

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun EnemyBottomBar(
    appState: EnemyAppState,
    destinationState: EnemyState.DestinationState,
    appControllerState: EnemyState.AppControllerState,
    controllerState: ControllerState,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = appControllerState.isVisible,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically(),
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth(),
        ) {
            AnimatedVisibility(
                visible = controllerState.isVisible,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically(),
                modifier = Modifier.fillMaxWidth(),
            ) {
                EnemyController(
                    controllerState = controllerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .clickable { appControllerState.onClick() }
                        .let {
                            if (!destinationState.isBottomBarVisible) return@let it
                            it.consumedWindowInsets(WindowInsets.navigationBars)
                        },
                )
            }

            AnimatedVisibility(
                visible = destinationState.isBottomBarVisible,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically(),
                modifier = Modifier.fillMaxWidth(),
            ) {
                EnemyBottomBar(
                    appState = appState,
                    destinationState = destinationState,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
private fun EnemyController(
    controllerState: ControllerState,
    modifier: Modifier = Modifier,
) {
    ControllerBottomSheet(
        state = controllerState,
        modifier = modifier,
    )
}

@Composable
private fun EnemyBottomBar(
    appState: EnemyAppState,
    destinationState: EnemyState.DestinationState,
    modifier: Modifier = Modifier,
) {
    NavigationBar(modifier) {
        destinationState.destinations.forEach {
            val painterResId: Int = when (it) {
                is EnemyDestination.Top.Browse -> drawables.browse
            }

            val stringResId: Int = when (it) {
                is EnemyDestination.Top.Browse -> strings.browse
            }

            NavigationBarItem(
                selected = appState.isDestinationSelected(it),
                onClick = {
                    appState.navigate(it.route) {
                        popUpTo(EnemyDestination.startDestination.route) {
                            saveState = true
                            inclusive = false
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(painterResId),
                        contentDescription = stringResource(stringResId),
                        modifier = Modifier.size(dimensionResource(dimens.icon_medium)),
                    )
                },
                modifier = Modifier.navigationBarsPadding(),
            )
        }
    }
}
