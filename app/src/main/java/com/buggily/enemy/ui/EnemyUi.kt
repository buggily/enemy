package com.buggily.enemy.ui

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.buggily.enemy.R
import com.buggily.enemy.controller.ControllerBottomSheet
import com.buggily.enemy.controller.ControllerScreen
import com.buggily.enemy.core.navigation.NavigationDestination
import com.buggily.enemy.core.ui.GlobalUiState
import com.buggily.enemy.core.ui.GlobalUiViewModel
import com.buggily.enemy.core.ui.LocalWindowSizeClass
import com.buggily.enemy.core.ui.ext.ZERO
import com.buggily.enemy.core.ui.ui.IconButton
import com.buggily.enemy.core.ui.ui.IconFloatingActionButton
import com.buggily.enemy.core.ui.ui.SingleLineText
import com.buggily.enemy.core.ui.ui.SingleLineTextField
import com.buggily.enemy.core.ui.ui.picker.PickerDialog
import com.buggily.enemy.core.ui.ui.picker.playlist.PlaylistPickerViewModel
import com.buggily.enemy.core.ui.ui.picker.track.TrackPickerViewModel
import com.buggily.enemy.feature.album.AlbumScreen
import com.buggily.enemy.feature.browse.BrowseScreen
import com.buggily.enemy.feature.orientation.OrientationScreen
import com.buggily.enemy.feature.playlist.PlaylistScreen
import com.buggily.enemy.feature.playlist.create.CreatePlaylistDialog
import com.buggily.enemy.feature.playlist.track.PlaylistTrackPickerViewModel
import com.buggily.enemy.feature.preferences.PreferencesScreen
import com.buggily.enemy.feature.track.playlist.TrackPlaylistPickerDialog
import com.buggily.enemy.core.ui.R as CR

@Composable
fun EnemyApp(
    appState: EnemyAppState,
    viewModel: GlobalUiViewModel,
    modifier: Modifier = Modifier,
) {
    val hostState: SnackbarHostState = remember { SnackbarHostState() }
    val uiState: GlobalUiState by viewModel.uiState.collectAsStateWithLifecycle()

    CompositionLocalProvider(LocalWindowSizeClass provides appState.windowSizeClass) {
        EnemyApp(
            appState = appState,
            hostState = hostState,
            uiState = uiState,
            modifier = modifier,
        )
    }
}

@Composable
private fun EnemyApp(
    appState: EnemyAppState,
    hostState: SnackbarHostState,
    uiState: GlobalUiState,
    modifier: Modifier = Modifier,
) {
    EnemyApp(
        appState = appState,
        hostState = hostState,
        searchState = uiState.searchState,
        destinationState = uiState.destinationState,
        preferencesState = uiState.preferencesState,
        createPlaylistState = uiState.createPlaylistState,
        controllerState = uiState.controllerState,
        modifier = modifier,
    )
}

@Composable
private fun EnemyApp(
    appState: EnemyAppState,
    hostState: SnackbarHostState,
    searchState: GlobalUiState.SearchState,
    destinationState: GlobalUiState.DestinationState,
    preferencesState: GlobalUiState.PreferencesState,
    createPlaylistState: GlobalUiState.CreatePlaylistState,
    controllerState: GlobalUiState.ControllerState,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        snackbarHost = { SnackbarHost(hostState) },
        contentWindowInsets = WindowInsets.ZERO,
        modifier = modifier.imePadding(),
        bottomBar = {
            EnemyBottomAppBar(
                searchState = searchState,
                destinationState = destinationState,
                preferencesState = preferencesState,
                createPlaylistState = createPlaylistState,
                controllerState = controllerState,
                modifier = Modifier.fillMaxWidth(),
            )
        },
    ) { padding: PaddingValues ->
        NavHost(
            navController = appState.navController,
            startDestination = NavigationDestination.startDestination.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding),
        ) {
            val contentModifier: Modifier = Modifier.fillMaxSize()

            composable(
                route = NavigationDestination.Orientation.route,
                arguments = NavigationDestination.Orientation.arguments,
            ) {
                OrientationScreen(
                    viewModel = hiltViewModel(),
                    modifier = contentModifier,
                )
            }

            composable(
                route = NavigationDestination.Browse.route,
                arguments = NavigationDestination.Browse.arguments,
            ) {
                BrowseScreen(
                    viewModel = hiltViewModel(),
                    modifier = contentModifier,
                )
            }

            composable(
                route = NavigationDestination.Album.route,
                arguments = NavigationDestination.Album.arguments,
            ) {
                AlbumScreen(
                    viewModel = hiltViewModel(),
                    modifier = contentModifier,
                )
            }

            composable(
                route = NavigationDestination.Preferences.route,
                arguments = NavigationDestination.Preferences.arguments,
            ) {
                PreferencesScreen(
                    viewModel = hiltViewModel(),
                    modifier = contentModifier,
                )
            }

            composable(
                route = NavigationDestination.Controller.route,
                arguments = NavigationDestination.Controller.arguments,
            ) {
                val context: Context = LocalContext.current
                val activity: ComponentActivity = checkNotNull(context as? ComponentActivity)

                ControllerScreen(
                    viewModel = hiltViewModel(activity),
                    modifier = contentModifier,
                )
            }

            composable(
                route = NavigationDestination.Playlist.route,
                arguments = NavigationDestination.Playlist.arguments,
            ) {
                PlaylistScreen(
                    viewModel = hiltViewModel(),
                    modifier = contentModifier,
                )
            }

            dialog(
                route = NavigationDestination.Playlist.Create.route,
                arguments = NavigationDestination.Playlist.Create.arguments,
                dialogProperties = DialogProperties(usePlatformDefaultWidth = false),
            ) {
                Box(Modifier.fillMaxWidth(3 / 4f)) {
                    CreatePlaylistDialog(hiltViewModel())
                }
            }

            dialog(
                route = NavigationDestination.Playlist.Picker.route,
                arguments = NavigationDestination.Playlist.Picker.arguments,
                dialogProperties = DialogProperties(usePlatformDefaultWidth = false),
            ) {
                val viewModel: PlaylistPickerViewModel = hiltViewModel()

                Box(Modifier.fillMaxWidth(3 / 4f)) {
                    PickerDialog(viewModel)
                }
            }

            dialog(
                route = NavigationDestination.Playlist.TrackPicker.route,
                arguments = NavigationDestination.Playlist.TrackPicker.arguments,
                dialogProperties = DialogProperties(usePlatformDefaultWidth = false),
            ) {
                val viewModel: PlaylistTrackPickerViewModel = hiltViewModel()

                Box(Modifier.fillMaxWidth(3 / 4f)) {
                    PickerDialog(viewModel)
                }
            }

            dialog(
                route = NavigationDestination.Track.Picker.route,
                arguments = NavigationDestination.Track.Picker.arguments,
                dialogProperties = DialogProperties(usePlatformDefaultWidth = false),
            ) {
                val viewModel: TrackPickerViewModel = hiltViewModel()

                Box(Modifier.fillMaxWidth(3 / 4f)) {
                    PickerDialog(viewModel)
                }
            }

            dialog(
                route = NavigationDestination.Track.PlaylistPicker.route,
                arguments = NavigationDestination.Track.PlaylistPicker.arguments,
                dialogProperties = DialogProperties(usePlatformDefaultWidth = false),
            ) {
                Box(Modifier.fillMaxWidth(3 / 4f)) {
                    TrackPlaylistPickerDialog(hiltViewModel())
                }
            }
        }
    }
}

@Composable
private fun EnemyBottomAppBar(
    searchState: GlobalUiState.SearchState,
    destinationState: GlobalUiState.DestinationState,
    preferencesState: GlobalUiState.PreferencesState,
    createPlaylistState: GlobalUiState.CreatePlaylistState,
    controllerState: GlobalUiState.ControllerState,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        AnimatedVisibility(
            visible = destinationState.isControllerVisible,
            enter = expandVertically(),
            exit = shrinkVertically(),
        ) {
            EnemyController(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .clickable { controllerState.to() }
                    .let {
                        if (destinationState.isBottomBarVisible) {
                            it.consumeWindowInsets(WindowInsets.systemBars)
                        } else {
                            it.consumeWindowInsets(WindowInsets.statusBars)
                        }
                    },
            )
        }

        AnimatedVisibility(
            visible = destinationState.isBottomBarVisible,
            enter = expandVertically(),
            exit = shrinkVertically(),
        ) {
            EnemyBottomBar(
                searchState = searchState,
                destinationState = destinationState,
                preferencesState = preferencesState,
                createPlaylistState = createPlaylistState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.bottom_bar)),
            )
        }
    }
}

@Composable
private fun EnemyController(
    modifier: Modifier = Modifier,
) {
    val context: Context = LocalContext.current
    val activity: ComponentActivity = checkNotNull(context as? ComponentActivity)

    ControllerBottomSheet(
        viewModel = hiltViewModel(activity),
        modifier = modifier,
    )
}

@Composable
private fun EnemyBottomBar(
    searchState: GlobalUiState.SearchState,
    destinationState: GlobalUiState.DestinationState,
    preferencesState: GlobalUiState.PreferencesState,
    createPlaylistState: GlobalUiState.CreatePlaylistState,
    modifier: Modifier = Modifier,
) {
    val iconButtonModifier: Modifier = Modifier.size(dimensionResource(CR.dimen.icon_medium))

    Surface(modifier) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = dimensionResource(CR.dimen.padding_large),
                alignment = Alignment.Start,
            ),
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(dimensionResource(CR.dimen.padding_large)),
        ) {
            if (searchState.isSearchButtonVisible) {
                EnemyBottomBarSearchIconButton(
                    searchState = searchState,
                    modifier = iconButtonModifier,
                )
            }

            if (searchState.isPreferencesButtonVisible) {
                EnemyBottomBarPreferencesIconButton(
                    preferencesState = preferencesState,
                    destinationState = destinationState,
                    modifier = iconButtonModifier,
                )
            }

            AnimatedVisibility(
                visible = searchState.isSearchTextVisible,
                enter = expandHorizontally(),
                exit = ExitTransition.None,
                modifier = Modifier.weight(1f),
            ) {
                EnemySearchTextField(
                    searchState = searchState,
                    modifier = modifier
                )
            }

            if (!searchState.isSearchTextVisible) {
                Spacer(Modifier.weight(1f))
            }

            EnemyBottomBarFloatingActionButton(
                createPlaylistState = createPlaylistState,
                modifier = Modifier,
            )
        }
    }
}

@Composable
private fun EnemyBottomBarSearchIconButton(
    searchState: GlobalUiState.SearchState,
    modifier: Modifier = Modifier,
) {
    val painterResId: Int = if (searchState.isVisible) {
        CR.drawable.search_disable
    } else {
        CR.drawable.search_enable
    }

    val stringResId: Int = if (searchState.isVisible) {
        CR.string.search_disable
    } else {
        CR.string.search_enable
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
    preferencesState: GlobalUiState.PreferencesState,
    destinationState: GlobalUiState.DestinationState,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = destinationState.isPreferencesButtonVisible,
        enter = expandHorizontally(),
        exit = shrinkHorizontally(),
    ) {
        IconButton(
            painter = painterResource(CR.drawable.preferences),
            contentDescription = stringResource(R.string.app_preferences),
            onClick = preferencesState.to,
            contentModifier = modifier,
        )
    }
}

@Composable
private fun EnemySearchTextField(
    searchState: GlobalUiState.SearchState,
    modifier: Modifier = Modifier,
) {
    SingleLineTextField(
        value = searchState.value,
        onValueChange = searchState.onChange,
        label = { BrowseSearchLabel() },
        trailingIcon = { BrowseSearchTrailingIcon(searchState) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
        ),
        modifier = modifier,
    )
}

@Composable
private fun BrowseSearchLabel(
    modifier: Modifier = Modifier,
) {
    SingleLineText(
        text = stringResource(CR.string.search),
        modifier = modifier,
    )
}

@Composable
private fun BrowseSearchTrailingIcon(
    searchState: GlobalUiState.SearchState,
    modifier: Modifier = Modifier,
) {
    IconButton(
        painter = painterResource(CR.drawable.clear),
        contentDescription = stringResource(CR.string.clear),
        onClick = searchState.onClear,
        modifier = modifier,
        contentModifier = Modifier.size(dimensionResource(CR.dimen.icon_medium)),
    )
}

@Composable
private fun EnemyBottomBarFloatingActionButton(
    createPlaylistState: GlobalUiState.CreatePlaylistState,
    modifier: Modifier = Modifier,
) {
    IconFloatingActionButton(
        painter = painterResource(CR.drawable.create),
        contentDescription = stringResource(CR.string.create),
        onClick = createPlaylistState.to,
        modifier = modifier,
    )
}
