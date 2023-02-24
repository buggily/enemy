package com.buggily.enemy.ui

import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.buggily.enemy.R
import com.buggily.enemy.albums.AlbumsState
import com.buggily.enemy.controller.ControllerBottomSheet
import com.buggily.enemy.controller.ControllerScreen
import com.buggily.enemy.controller.ControllerState
import com.buggily.enemy.core.ext.readPermission
import com.buggily.enemy.core.model.TimeOfDay
import com.buggily.enemy.core.ui.UiState
import com.buggily.enemy.core.ui.UiViewModel
import com.buggily.enemy.core.ui.composable.IconButton
import com.buggily.enemy.core.ui.composable.IconFloatingActionButton
import com.buggily.enemy.core.ui.composable.SingleLineText
import com.buggily.enemy.core.ui.composable.SingleLineTextField
import com.buggily.enemy.core.ui.ext.ZERO
import com.buggily.enemy.feature.album.AlbumScreen
import com.buggily.enemy.feature.album.AlbumState
import com.buggily.enemy.feature.orientation.OrientationScreen
import com.buggily.enemy.feature.orientation.OrientationState
import com.buggily.enemy.feature.preferences.PreferencesScreen
import com.buggily.enemy.navigation.EnemyDestination
import com.buggily.enemy.tracks.TracksState
import com.buggily.enemy.ui.browse.BrowseScreen
import com.buggily.enemy.ui.browse.BrowseState
import com.buggily.enemy.ui.browse.BrowseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
    uiViewModel: UiViewModel,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier,
) {
    val appState: EnemyAppState = rememberEnemyAppState(
        navController = rememberNavController(),
        windowSizeClass = windowSizeClass,
    )

    val hostState: SnackbarHostState = remember { SnackbarHostState() }
    val state: EnemyState by viewModel.state.collectAsStateWithLifecycle()
    val uiState: UiState by uiViewModel.state.collectAsStateWithLifecycle()

    EnemyApp(
        appState = appState,
        hostState = hostState,
        greetingState = uiState.greetingState,
        searchState = uiState.searchState,
        fabState = uiState.fabState,
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
    greetingState: UiState.GreetingState,
    searchState: UiState.SearchState,
    fabState: UiState.FabState,
    controllerState: ControllerState,
    albumTrackState: AlbumState.TrackState,
    tracksTrackState: TracksState.TrackState,
    modifier: Modifier = Modifier,
) {
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
        val route: String = EnemyDestination.Browse.getRoute(
            tab = BrowseState.TabState.Tab.Albums,
        )

        appState.navigate(route) {
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
                searchState = searchState,
                fabState = fabState,
                controllerState = controllerState,
                modifier = Modifier.fillMaxWidth(),
            )
        },
        snackbarHost = { SnackbarHost(hostState) },
        contentWindowInsets = WindowInsets.ZERO,
        modifier = modifier.imePadding(),
    ) { padding: PaddingValues ->
        val permissionResult: Int = ContextCompat.checkSelfPermission(
            LocalContext.current,
            readPermission
        )

        LaunchedEffect(Unit) {
            val isGranted: Boolean = permissionResult == PackageManager.PERMISSION_GRANTED
            if (!isGranted) onNotGranted()
        }

        val snackbarText: String = remember(greetingState) {
            when (greetingState.timeOfDay) {
                is TimeOfDay.Morning -> R.string.morning
                is TimeOfDay.Afternoon -> R.string.afternoon
                is TimeOfDay.Evening -> R.string.evening
                else -> R.string.day
            }
        }.let { stringResource(R.string.greeting, stringResource(it)) }

        LaunchedEffect(greetingState) {
            if (!greetingState.isVisible) return@LaunchedEffect
            hostState.showSnackbar(snackbarText)
            greetingState.onVisible()
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
                val activity: ComponentActivity = LocalContext.current as ComponentActivity
                val uiViewModel: UiViewModel = hiltViewModel { activity.viewModelStore }
                val viewModel: BrowseViewModel = hiltViewModel()

                LaunchedEffect(Unit) {
                    val fabEventState: Flow<UiState.FabEventState> = uiViewModel.state.map {
                        it.fabEventState
                    }

                    fabEventState.collect {
                        when (it) {
                            is UiState.FabEventState.Event -> {
                                when (it) {
                                    is UiState.FabEventState.Event.Click -> {
                                        viewModel.onFloatingActionButtonClick()
                                    }
                                }

                                it.onEvent()
                            }
                            else -> Unit
                        }
                    }
                }

                BrowseScreen(
                    viewModel = viewModel,
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
    searchState: UiState.SearchState,
    fabState: UiState.FabState,
    controllerState: ControllerState,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth(),
    ) {
        AnimatedVisibility(
            visible = controllerState.isVisible && appState.isControllerVisible,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically(),
            modifier = Modifier.fillMaxWidth(),
        ) {
            EnemyController(
                controllerState = controllerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .clickable {
                        appState.navigate(EnemyDestination.Controller.route) {
                            launchSingleTop = true
                            restoreState = false
                        }
                    }
                    .let {
                        if (appState.isBottomBarVisible) {
                            it.consumedWindowInsets(WindowInsets.systemBars)
                        } else {
                            it.consumedWindowInsets(WindowInsets.statusBars)
                        }
                    },
            )
        }

        AnimatedVisibility(
            visible = appState.isBottomBarVisible,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically(),
            modifier = modifier,
        ) {
            EnemyBottomBar(
                appState = appState,
                searchState = searchState,
                fabState = fabState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.bottom_bar)),
            )
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
    searchState: UiState.SearchState,
    fabState: UiState.FabState,
    modifier: Modifier = Modifier,
) {
    val iconButtonModifier: Modifier = Modifier.size(dimensionResource(dimens.icon_medium))

    Surface(modifier) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = dimensionResource(dimens.padding_large),
                alignment = Alignment.Start,
            ),
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(dimensionResource(dimens.padding_large)),
        ) {
            AnimatedVisibility(
                visible = searchState.isVisible,
                enter = fadeIn() + expandHorizontally(),
                exit = ExitTransition.None,
                modifier = Modifier.weight(1f),
            ) {
                EnemyBottomBarSearch(
                    searchState = searchState,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            if (!searchState.isVisible) {
                EnemyBottomBarSearchIconButton(
                    searchState = searchState,
                    modifier = iconButtonModifier,
                )

                EnemyBottomBarPreferencesIconButton(
                    appState = appState,
                    modifier = iconButtonModifier,
                )

                Spacer(Modifier.weight(1f))
            }

            EnemyBottomBarFloatingActionButton(
                fabState = fabState,
                modifier = Modifier,
            )
        }
    }
}

@Composable
private fun EnemyBottomBarSearchIconButton(
    searchState: UiState.SearchState,
    modifier: Modifier = Modifier,
) {
    val painterResId: Int = if (searchState.isEnabled) {
        drawables.search_disable
    } else {
        drawables.search_enable
    }

    val stringResId: Int = if (searchState.isEnabled) {
        strings.search_disable
    } else {
        strings.search_enable
    }

    IconButton(
        onClick = searchState.onClick,
        painter = painterResource(painterResId),
        contentDescription = stringResource(stringResId),
        contentModifier = modifier,
    )
}

@Composable
private fun RowScope.EnemyBottomBarPreferencesIconButton(
    appState: EnemyAppState,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = appState.isPreferencesButtonVisible,
        enter = fadeIn() + expandHorizontally(),
        exit = fadeOut() + shrinkHorizontally(),
    ) {
        IconButton(
            painter = painterResource(drawables.preferences),
            contentDescription = stringResource(R.string.preferences),
            contentModifier = modifier,
        ) {
            appState.navigate(EnemyDestination.Preferences.route) {
                launchSingleTop = true
                restoreState = false
            }
        }
    }
}

@Composable
private fun EnemyBottomBarSearch(
    searchState: UiState.SearchState,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = searchState.isVisible,
        enter = fadeIn() + expandHorizontally(),
        exit = fadeOut() + shrinkHorizontally(),
        modifier = modifier,
    ) {
        EnemySearchTextField(
            searchState = searchState,
            modifier = Modifier,
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun EnemySearchTextField(
    searchState: UiState.SearchState,
    modifier: Modifier = Modifier,
) {
    SingleLineTextField(
        value = searchState.value,
        onValueChange = searchState.onChange,
        label = { BrowseSearchLabel() },
        trailingIcon = { BrowseSearchTrailingIcon(searchState) },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
        ),
        modifier = modifier,
    )
}

@Composable
private fun BrowseSearchLabel(
    modifier: Modifier = Modifier,
) {
    SingleLineText(
        text = stringResource(R.string.search),
        modifier = modifier,
    )
}

@Composable
private fun BrowseSearchTrailingIcon(
    searchState: UiState.SearchState,
    modifier: Modifier = Modifier,
) {
    IconButton(
        painter = painterResource(drawables.clear),
        contentDescription = stringResource(strings.clear),
        onClick = searchState.onClear,
        modifier = modifier,
        contentModifier = Modifier.size(dimensionResource(dimens.icon_medium)),
    )
}

@Composable
private fun EnemyBottomBarFloatingActionButton(
    fabState: UiState.FabState,
    modifier: Modifier = Modifier,
) {
    IconFloatingActionButton(
        painter = painterResource(drawables.create),
        contentDescription = stringResource(strings.create),
        onClick = fabState.onClick,
        modifier = modifier,
    )
}
