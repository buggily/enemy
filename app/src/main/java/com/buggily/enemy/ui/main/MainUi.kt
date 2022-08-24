package com.buggily.enemy.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.media3.common.MediaItem
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.buggily.enemy.R
import com.buggily.enemy.ext.isHorizontalImeVisible
import com.buggily.enemy.ext.isNonNull
import com.buggily.enemy.ui.ContentAlpha
import com.buggily.enemy.ui.EnemyDestination
import com.buggily.enemy.ui.EnemyState
import com.buggily.enemy.ui.album.AlbumScreen
import com.buggily.enemy.ui.ext.ArtImage
import com.buggily.enemy.ui.ext.CollapsableButton
import com.buggily.enemy.ui.ext.IconButton
import com.buggily.enemy.ui.ext.IconFloatingActionButton
import com.buggily.enemy.ui.ext.SingleLineText
import com.buggily.enemy.ui.home.HomeScreen
import com.buggily.enemy.ui.orientation.OrientationScreen
import com.buggily.enemy.ui.settings.SettingsScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    enemyState: EnemyState,
    modifier: Modifier = Modifier,
) {
    val state: MainState by viewModel.state.collectAsStateWithLifecycle()

    MainScreen(
        navController = enemyState.navController,
        collapsableState = state.collapsableState,
        controllerState = state.controllerState,
        modifier = modifier,
        contentModifier = Modifier.fillMaxSize(),
    ) {
        MainScreenContent(
            enemyState = enemyState,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
private fun MainScreen(
    navController: NavHostController,
    collapsableState: MainState.CollapsableState,
    controllerState: MainState.ControllerState,
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val isHorizontalImeVisible: Boolean = LocalConfiguration.current.isHorizontalImeVisible

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
    ) {
        Box(contentModifier.weight(1f)) {
            content()

            when (isHorizontalImeVisible) {
                false -> MainCollapsableButton(
                    navController = navController,
                    collapsableState = collapsableState,
                    modifier = Modifier
                        .fillMaxSize(1 / 2f)
                        .align(Alignment.BottomEnd)
                        .padding(dimensionResource(R.dimen.padding_larger)),
                )
                else -> Unit
            }
        }

        when (isHorizontalImeVisible) {
            false -> MainController(
                controllerState = controllerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
            )
            else -> Unit
        }
    }
}

@Composable
private fun MainScreenContent(
    enemyState: EnemyState,
    modifier: Modifier = Modifier,
) {
    val navController: NavHostController = enemyState.navController
    val viewModelStoreOwner: ViewModelStoreOwner = enemyState.activityViewModelStoreOwner

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
                enemyState = enemyState,
                viewModel = hiltViewModel(),
                modifier = contentModifier.systemBarsPadding(),
            )
        }

        composable(
            route = EnemyDestination.Home.route,
            arguments = EnemyDestination.Home.arguments,
            deepLinks = EnemyDestination.Home.deepLinks,
        ) {
            HomeScreen(
                enemyState = enemyState,
                viewModel = hiltViewModel(),
                mainViewModel = hiltViewModel(viewModelStoreOwner),
                modifier = contentModifier.statusBarsPadding(),
            )
        }

        composable(
            route = EnemyDestination.Album.route,
            arguments = EnemyDestination.Album.arguments,
            deepLinks = EnemyDestination.Album.deepLinks,
        ) {
            AlbumScreen(
                viewModel = hiltViewModel(),
                mainViewModel = hiltViewModel(viewModelStoreOwner),
                modifier = contentModifier,
            )
        }

        composable(
            route = EnemyDestination.Settings.route,
            arguments = EnemyDestination.Settings.arguments,
            deepLinks = EnemyDestination.Settings.deepLinks,
        ) {
            SettingsScreen(
                viewModel = hiltViewModel(),
                modifier = contentModifier.systemBarsPadding(),
            )
        }
    }
}

@Composable
private fun MainController(
    controllerState: MainState.ControllerState,
    modifier: Modifier = Modifier,
) {
    val itemState: MainState.ControllerState.ItemState = controllerState.itemState
    val shape: Shape = MaterialTheme.shapes.large.copy(
        bottomStart = ZeroCornerSize,
        bottomEnd = ZeroCornerSize,
    )

    AnimatedVisibility(
        visible = itemState.item.isNonNull(),
        enter = expandVertically(
            expandFrom = Alignment.Bottom,
        ),
        exit = shrinkVertically(
            shrinkTowards = Alignment.Top,
        ),
        modifier = modifier,
    ) {
        Surface(
            shape = shape,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxSize(),
        ) {
            MainControllerBackground(
                itemState = itemState,
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(ContentAlpha.LOW),
            )

            MainControllerForeground(
                controllerState = controllerState,
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_large)),
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
        else -> Unit
    }
}

@Composable
private fun MainControllerForeground(
    controllerState: MainState.ControllerState,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            space = dimensionResource(R.dimen.padding_medium),
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
            space = dimensionResource(R.dimen.padding_small),
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
            modifier = Modifier.alpha(ContentAlpha.MEDIUM),
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
    val isPlay: Boolean = playState.isPlay
    val painterResId: Int = if (isPlay) R.drawable.pause else R.drawable.play
    val stringResId: Int = if (isPlay) R.string.pause else R.string.play

    IconButton(
        onClick = playState.onPlayClick,
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
        onClick = nextState.onNextClick,
        enabled = nextState.isNext,
        painter = painterResource(R.drawable.next),
        contentDescription = stringResource(R.string.next),
        modifier = modifier,
    )
}

@Composable
private fun MainControllerPreviousButton(
    previousState: MainState.ControllerState.PreviousState,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = previousState.onPreviousClick,
        enabled = previousState.isPrevious,
        painter = painterResource(R.drawable.previous),
        contentDescription = stringResource(R.string.previous),
        modifier = modifier,
    )
}

@Composable
private fun MainCollapsableButton(
    navController: NavHostController,
    collapsableState: MainState.CollapsableState,
    modifier: Modifier = Modifier,
) {
    var destination: NavDestination? by remember { mutableStateOf(navController.currentDestination) }
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val lifecycle: Lifecycle = lifecycleOwner.lifecycle

    LaunchedEffect(Unit) {
        navController.currentBackStackEntryFlow.flowWithLifecycle(lifecycle).collectLatest {
            destination = it.destination
        }
    }

    val searchState: MainState.CollapsableState.SearchState = collapsableState.searchState
    val repeatState: MainState.CollapsableState.RepeatState = collapsableState.repeatState
    val shuffleState: MainState.CollapsableState.ShuffleState = collapsableState.shuffleState

    val isCollapsable: Boolean = collapsableState.isCollapsable
    val painterResId: Int = if (isCollapsable) R.drawable.close else R.drawable.open
    val contentDescriptionResId: Int = if (isCollapsable) R.drawable.close else R.string.open

    val contents: List<@Composable () -> Unit> = when (EnemyDestination.get(destination)) {
        is EnemyDestination.Home -> listOf(
            { MainRepeatButton(repeatState) },
            { MainSearchButton(searchState) },
            { MainShuffleButton(shuffleState) },
        )
        is EnemyDestination.Album -> listOf(
            { MainRepeatButton(repeatState) },
            { MainShuffleButton(shuffleState) },
        )
        else -> return
    }

    CollapsableButton(
        isCollapsable = isCollapsable,
        alignment = Alignment.BottomEnd,
        contents = contents,
        modifier = modifier,
    ) {
        IconFloatingActionButton(
            painter = painterResource(painterResId),
            contentDescription = stringResource(contentDescriptionResId),
            onClick = collapsableState.onCollapsableClick,
        )
    }
}

@Composable
private fun MainSearchButton(
    searchState: MainState.CollapsableState.SearchState,
    modifier: Modifier = Modifier,
) {
    val isSearch: Boolean = searchState.isSearch
    val painterResId: Int = if (isSearch) R.drawable.search_disable else R.drawable.search_enable
    val contentDescriptionResId: Int = if (isSearch) R.string.search_disable else R.string.search_enable

    IconFloatingActionButton(
        onClick = searchState.onSearchClick,
        painter = painterResource(painterResId),
        contentDescription = stringResource(contentDescriptionResId),
        modifier = modifier,
    )
}

@Composable
private fun MainRepeatButton(
    repeatState: MainState.CollapsableState.RepeatState,
    modifier: Modifier = Modifier,
) {
    val isRepeat: Boolean = repeatState.isRepeat
    val painterResId: Int = if (isRepeat) R.drawable.repeat_disable else R.drawable.repeat_enable
    val contentDescriptionResId: Int = if (isRepeat) R.string.repeat_disable else R.string.repeat_enable
    val alpha: Float = if (isRepeat) ContentAlpha.MAX else ContentAlpha.MEDIUM

    IconFloatingActionButton(
        onClick = repeatState.onRepeatClick,
        painter = painterResource(painterResId),
        contentDescription = stringResource(contentDescriptionResId),
        modifier = modifier,
        contentModifier = Modifier.alpha(alpha),
    )
}

@Composable
private fun MainShuffleButton(
    shuffleState: MainState.CollapsableState.ShuffleState,
    modifier: Modifier = Modifier,
) {
    val isShuffle: Boolean = shuffleState.isShuffle
    val painterResId: Int = if (isShuffle) R.drawable.shuffle_disable else R.drawable.shuffle_enable
    val contentDescriptionResId: Int = if (isShuffle) R.string.shuffle_disable else R.string.shuffle_enable
    val alpha: Float = if (isShuffle) ContentAlpha.MAX else ContentAlpha.MEDIUM

    IconFloatingActionButton(
        onClick = shuffleState.onShuffleClick,
        painter = painterResource(painterResId),
        contentDescription = stringResource(contentDescriptionResId),
        modifier = modifier,
        contentModifier = Modifier.alpha(alpha),
    )
}
