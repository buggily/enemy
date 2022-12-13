package com.buggily.enemy.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.media3.common.MediaItem
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.buggily.enemy.core.ui.ArtImage
import com.buggily.enemy.core.ui.IconButton
import com.buggily.enemy.core.ui.SingleLineText
import com.buggily.enemy.core.ui.theme.ContentAlpha
import com.buggily.enemy.feature.album.AlbumScreen
import com.buggily.enemy.feature.album.AlbumState
import com.buggily.enemy.feature.album.AlbumsState
import com.buggily.enemy.feature.orientation.OrientationScreen
import com.buggily.enemy.feature.orientation.OrientationState
import com.buggily.enemy.feature.preferences.PreferencesScreen
import com.buggily.enemy.ui.EnemyDestination
import com.buggily.enemy.ui.home.HomeScreen
import com.buggily.enemy.core.ui.R.dimen as dimens
import com.buggily.enemy.core.ui.R.drawable as drawables
import com.buggily.enemy.core.ui.R.string as strings

@Composable
@OptIn(ExperimentalLifecycleComposeApi::class)
fun MainScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
) {
    val state: MainState by viewModel.state.collectAsStateWithLifecycle()
    val hostState: SnackbarHostState = remember { SnackbarHostState() }

    MainScreen(
        state = state,
        hostState = hostState,
        modifier = modifier,
    )
}

@Composable
fun MainScreen(
    state: MainState,
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    MainScreen(
        hostState = hostState,
        navigationState = state.navigationState,
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
private fun MainScreen(
    hostState: SnackbarHostState,
    navigationState: MainState.NavigationState,
    controllerState: MainState.ControllerState,
    albumTrackState: AlbumState.TrackState,
    modifier: Modifier = Modifier,
) {
    val contentPadding: WindowInsets = if (controllerState.isVisible) {
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
            MainController(
                controllerState = controllerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = hostState,
            )
        },
        contentWindowInsets = WindowInsets.ime,
        modifier = modifier,
    ) {
        MainScreenContent(
            navigationState = navigationState,
            albumTrackState = albumTrackState,
            contentInsets = contentPadding,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .consumedWindowInsets(it)
                .consumedWindowInsets(WindowInsets.navigationBars),
        )
    }
}

@Composable
private fun MainScreenContent(
    navigationState: MainState.NavigationState,
    albumTrackState: AlbumState.TrackState,
    contentInsets: WindowInsets,
    modifier: Modifier = Modifier,
) {
    val navController: NavHostController = rememberNavController()
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val lifecycle: Lifecycle = lifecycleOwner.lifecycle

    LaunchedEffect(Unit) {
        navController.currentBackStackEntryFlow.flowWithLifecycle(lifecycle).collect {
            navigationState.onDestinationChange(it.destination)
        }
    }

    val orientationHomeState = OrientationState.HomeState {
        navController.navigate(EnemyDestination.Home.route) {
            launchSingleTop = true
            restoreState = false

            popUpTo(EnemyDestination.Orientation.route) {
                inclusive = true
                saveState = false
            }
        }
    }

    val albumsAlbumState = AlbumsState.AlbumState {
        navController.navigate(EnemyDestination.Album.getRoute(it.id)) {
            launchSingleTop = true
            restoreState = false
        }
    }

    NavHost(
        navController = navController,
        startDestination = EnemyDestination.Orientation.route,
        modifier = modifier,
    ) {
        val contentModifier: Modifier = Modifier.fillMaxSize()

        composable(
            route = EnemyDestination.Orientation.route,
            arguments = EnemyDestination.Orientation.arguments,
            deepLinks = EnemyDestination.Orientation.deepLinks,
        ) {
            OrientationScreen(
                homeState = orientationHomeState,
                viewModel = hiltViewModel(),
                modifier = contentModifier,
            )
        }

        composable(
            route = EnemyDestination.Home.route,
            arguments = EnemyDestination.Home.arguments,
            deepLinks = EnemyDestination.Home.deepLinks,
        ) {
            HomeScreen(
                viewModel = hiltViewModel(),
                albumState = albumsAlbumState,
                contentPadding = WindowInsets(
                    left = dimensionResource(dimens.padding_large),
                    top = dimensionResource(dimens.padding_large),
                    right = dimensionResource(dimens.padding_large),
                    bottom = dimensionResource(dimens.padding_large),
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

@Composable
private fun MainController(
    controllerState: MainState.ControllerState,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = controllerState.isVisible,
        enter = expandVertically(),
        exit = shrinkVertically(),
        modifier = modifier,
    ) {
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.fillMaxSize(),
        ) {
            MainControllerBackground(
                itemState = controllerState.itemState,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(ContentAlpha.low),
            )

            MainControllerForeground(
                controllerState = controllerState,
                modifier = Modifier
                    .padding(dimensionResource(dimens.padding_large))
                    .navigationBarsPadding(),
            )
        }
    }
}

@Composable
private fun MainControllerBackground(
    itemState: MainState.ControllerState.ItemState,
    modifier: Modifier = Modifier,
) {
    when (val item: MediaItem? = remember(itemState) { itemState.item }) {
        is MediaItem -> ArtImage(
            item = item,
            contentScale = ContentScale.Crop,
            modifier = modifier,
        )
    }
}

@Composable
private fun MainControllerForeground(
    controllerState: MainState.ControllerState,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = dimensionResource(dimens.padding_medium),
            alignment = Alignment.Start,
        ),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        MainControllerText(
            itemState = controllerState.itemState,
            modifier = Modifier.weight(1f),
        )

        MainControllerControls(
            playState = controllerState.playState,
            nextState = controllerState.nextState,
            previousState = controllerState.previousState,
        )
    }
}

@Composable
private fun MainControllerText(
    itemState: MainState.ControllerState.ItemState,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(dimens.padding_small),
            alignment = Alignment.Top,
        ),
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        SingleLineText(
            text = itemState.nameText,
            style = MaterialTheme.typography.titleMedium,
        )

        SingleLineText(
            text = itemState.artistText,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.alpha(ContentAlpha.medium),
        )
    }
}

@Composable
private fun MainControllerControls(
    playState: MainState.ControllerState.PlayState,
    nextState: MainState.ControllerState.NextState,
    previousState: MainState.ControllerState.PreviousState,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        MainControllerPreviousButton(
            previousState = previousState,
        )

        MainControllerPlayButton(
            playState = playState,
        )

        MainControllerNextButton(
            nextState = nextState,
        )
    }
}

@Composable
private fun MainControllerPlayButton(
    playState: MainState.ControllerState.PlayState,
    modifier: Modifier = Modifier,
) {
    val isPlaying: Boolean = playState.isPlaying
    val painterResId: Int = if (isPlaying) drawables.pause else drawables.play
    val stringResId: Int = if (isPlaying) strings.pause else strings.play

    IconButton(
        onClick = playState.onClick,
        enabled = playState.isEnabled,
        painter = painterResource(painterResId),
        contentDescription = stringResource(stringResId),
        modifier = modifier,
    )
}

@Composable
private fun MainControllerNextButton(
    nextState: MainState.ControllerState.NextState,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = nextState.onClick,
        enabled = nextState.isEnabled,
        painter = painterResource(drawables.next),
        contentDescription = stringResource(strings.next),
        modifier = modifier,
    )
}

@Composable
private fun MainControllerPreviousButton(
    previousState: MainState.ControllerState.PreviousState,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = previousState.onClick,
        enabled = previousState.isEnabled,
        painter = painterResource(drawables.previous),
        contentDescription = stringResource(strings.previous),
        modifier = modifier,
    )
}
