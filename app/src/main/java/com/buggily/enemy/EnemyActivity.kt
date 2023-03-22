package com.buggily.enemy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.buggily.enemy.controller.ControllerViewModel
import com.buggily.enemy.core.controller.ControllerEventState
import com.buggily.enemy.core.controller.ControllerOrchestratable
import com.buggily.enemy.core.model.theme.Theme
import com.buggily.enemy.core.navigation.NavigationArgs
import com.buggily.enemy.core.navigation.NavigationEventState
import com.buggily.enemy.core.navigation.NavigationOrchestratable
import com.buggily.enemy.di.DirectExecutorQualifier
import com.buggily.enemy.ui.EnemyApp
import com.buggily.enemy.ui.EnemyAppState
import com.buggily.enemy.ui.EnemyViewModel
import com.buggily.enemy.ui.rememberEnemyAppState
import com.buggily.enemy.ui.theme.EnemyPalette
import com.buggily.enemy.ui.theme.EnemyTheme
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@AndroidEntryPoint
class EnemyActivity : ComponentActivity() {

    private val viewModel: EnemyViewModel by viewModels()
    private val controllerViewModel: ControllerViewModel by viewModels()

    private var setPosition: Job? = null
    private lateinit var controllerFuture: ListenableFuture<MediaController>

    @Inject
    lateinit var navigationOrchestrator: NavigationOrchestratable

    @Inject
    lateinit var controllerOrchestrator: ControllerOrchestratable

    @Inject
    lateinit var sessionToken: SessionToken

    @Inject
    @DirectExecutorQualifier
    lateinit var executor: Executor

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(
            window,
            false
        )

        val insetsController = WindowInsetsControllerCompat(
            window,
            window.decorView
        )

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    controllerViewModel.isPlaying.collect {
                        if (it) startSetPosition() else stopSetPosition()
                    }
                }

                launch {
                    controllerOrchestrator.eventState.collect {
                        onControllerEvent(it)
                    }
                }
            }
        }

        setContent {
            val navController: NavHostController = rememberNavController()
            val windowSizeClass: WindowSizeClass = calculateWindowSizeClass(this)

            val appState: EnemyAppState = rememberEnemyAppState(
                navController = navController,
                windowSizeClass = windowSizeClass,
            )

            LaunchedEffect(Unit) {
                navController.removeOnDestinationChangedListener(destinationChangedListener)
                navController.addOnDestinationChangedListener(destinationChangedListener)
            }

            LaunchedEffect(Unit) {
                navigationOrchestrator.eventState.flowWithLifecycle(lifecycle).collect {
                    when (it) {
                        is NavigationEventState.Event -> when (val args: NavigationArgs = it.args) {
                            is NavigationArgs.Route.WithOptions -> navController.navigate(
                                route = args.route,
                                builder = args.builder,
                            )
                            is NavigationArgs.Route.WithoutOptions -> navController.navigate(
                                route = args.route,
                            )
                            is NavigationArgs.Back -> navController.popBackStack()
                        }
                        is NavigationEventState.Default -> Unit
                    }

                    if (it is NavigationEventState.Event) it.onEvent()
                }
            }

            val isSystemInDarkTheme: Boolean = isSystemInDarkTheme()
            val theme: Theme by viewModel.theme.collectAsStateWithLifecycle()

            val paletteTheme: EnemyPalette.Theme = remember(theme) {
                val isDynamic: Boolean = theme.dynamic.isOn

                when (theme.scheme) {
                    is Theme.Scheme.Default -> EnemyPalette.Theme.Default(
                        isDynamic = isDynamic,
                        isSystemInDarkTheme = isSystemInDarkTheme,
                    )
                    is Theme.Scheme.Light -> EnemyPalette.Theme.Light(
                        isDynamic = isDynamic,
                    )
                    is Theme.Scheme.Dark -> EnemyPalette.Theme.Dark(
                        isDynamic = isDynamic,
                    )
                }
            }

            val palette: EnemyPalette = remember(paletteTheme) { EnemyPalette(paletteTheme) }
            val isLight: Boolean = remember(palette) { palette.isLight }

            LaunchedEffect(isLight) {
                with(insetsController) {
                    isAppearanceLightStatusBars = isLight
                    isAppearanceLightNavigationBars = isLight
                }
            }

            EnemyTheme(palette) {
                EnemyApp(
                    appState = appState,
                    viewModel = hiltViewModel(this),
                    globalUiViewModel = hiltViewModel(this),
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()

        controllerFuture = MediaController.Builder(
            this,
            sessionToken
        ).buildAsync()

        lifecycleScope.launch {
            with(requireController()) {
                controllerViewModel.setMediaItem(currentMediaItem)

                controllerViewModel.setPlaybackState(playbackState)
                controllerViewModel.setIsPlaying(isPlaying)
                controllerViewModel.setRepeatMode(repeatMode)
                controllerViewModel.setShuffleMode(shuffleModeEnabled)

                controllerViewModel.setHasNext(hasNextMediaItem())
                controllerViewModel.setHasPrevious(hasPreviousMediaItem())

                controllerViewModel.setDuration(duration)
                controllerViewModel.setPosition(currentPosition)

                addListener(controllerListener)
            }
        }
    }

    override fun onStop() {
        super.onStop()

        lifecycleScope.launch {
            requireController().removeListener(controllerListener)
            MediaController.releaseFuture(controllerFuture)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseSetPosition()
    }

    private suspend fun onControllerEvent(event: ControllerEventState) {
        when (event) {
            is ControllerEventState.Event.Play -> with(requireController()) {
                if (event is ControllerEventState.Event.Play.With) {
                    setMediaItems(event.items)
                    seekToDefaultPosition(event.index)
                }

                prepare()
                play()
            }
            is ControllerEventState.Event.Pause -> {
                requireController().pause()
            }
            is ControllerEventState.Event.Next -> {
                requireController().seekToNextMediaItem()
            }
            is ControllerEventState.Event.Previous -> {
                requireController().seekToPreviousMediaItem()
            }
            is ControllerEventState.Event.Repeat -> {
                requireController().repeatMode = event.repeatMode
            }
            is ControllerEventState.Event.Shuffle -> {
                requireController().shuffleModeEnabled = event.shuffleMode
            }
            is ControllerEventState.Event.Seek -> {
                requireController().seekTo(event.milliseconds)
            }
            is ControllerEventState.Default -> Unit
        }

        if (event is ControllerEventState.Event) event.onEvent()
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun requireController(): MediaController = suspendCoroutine {
        val listener: () -> Unit = {
            try {
                it.resume(controllerFuture.get())
            } catch (e: Exception) {
                it.resumeWithException(e)
            }
        }

        controllerFuture.addListener(
            listener,
            executor,
        )
    }

    private val destinationChangedListener: NavController.OnDestinationChangedListener =
        NavController.OnDestinationChangedListener { _, it: NavDestination, _ ->
            viewModel.onDestinationChange(it)
        }

    private val controllerListener: Player.Listener = object : Player.Listener {

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            controllerViewModel.setIsPlaying(isPlaying)
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)
            controllerViewModel.setPlaybackState(playbackState)
        }

        override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
            super.onMediaMetadataChanged(mediaMetadata)

            val mediaItem: MediaItem = MediaItem.Builder()
                .setMediaMetadata(mediaMetadata)
                .build()

            controllerViewModel.setMediaItem(mediaItem)
        }

        override fun onRepeatModeChanged(repeatMode: Int) {
            super.onRepeatModeChanged(repeatMode)
            controllerViewModel.setRepeatMode(repeatMode)
        }

        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
            super.onShuffleModeEnabledChanged(shuffleModeEnabled)
            controllerViewModel.setShuffleMode(shuffleModeEnabled)
        }

        override fun onEvents(player: Player, events: Player.Events) {
            super.onEvents(player, events)

            if (
                events.containsAny(
                    Player.EVENT_MEDIA_ITEM_TRANSITION,
                    Player.EVENT_PLAYBACK_STATE_CHANGED,
                )
            ) {
                controllerViewModel.setDuration(player.duration)
            }

            if (
                events.containsAny(
                    Player.EVENT_MEDIA_ITEM_TRANSITION,
                    Player.EVENT_REPEAT_MODE_CHANGED,
                    Player.EVENT_SHUFFLE_MODE_ENABLED_CHANGED,
                )
            ) {
                controllerViewModel.setPlaybackState(player.playbackState)
                controllerViewModel.setHasNext(player.hasNextMediaItem())
                controllerViewModel.setHasPrevious(player.hasPreviousMediaItem())
            }
        }
    }

    private fun startSetPosition() {
        setPosition = lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                while (true) {
                    controllerViewModel.setPosition(requireController().currentPosition)
                    delay(timeMillis = 1000)
                }
            }
        }
    }

    private fun stopSetPosition() {
        setPosition?.cancel()
    }

    private fun releaseSetPosition() {
        stopSetPosition()
        setPosition = null
    }
}
