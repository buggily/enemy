package com.buggily.enemy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
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
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.buggily.enemy.core.model.theme.Theme
import com.buggily.enemy.di.DirectExecutorQualifier
import com.buggily.enemy.ui.EnemyApp
import com.buggily.enemy.ui.EnemyState
import com.buggily.enemy.ui.EnemyViewModel
import com.buggily.enemy.ui.theme.EnemyPalette
import com.buggily.enemy.ui.theme.EnemyTheme
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@AndroidEntryPoint
class EnemyActivity : ComponentActivity() {

    private val viewModel: EnemyViewModel by viewModels()

    private var setPosition: Job? = null
    private lateinit var controllerFuture: ListenableFuture<MediaController>

    @Inject
    lateinit var sessionToken: SessionToken

    @Inject
    @DirectExecutorQualifier
    lateinit var directExecutor: Executor

    @OptIn(
        ExperimentalLifecycleComposeApi::class,
        ExperimentalMaterial3WindowSizeClassApi::class,
    )

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
                viewModel.isPlaying.collect {
                    if (it) startSetPosition() else stopSetPosition()
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.DESTROYED) {
                releaseSetPosition()
            }
        }

        val controllerEventState: Flow<EnemyState.ControllerEventState> = viewModel.state.map {
            it.controllerEventState
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                controllerEventState.collect { onControllerEvent(it) }
            }
        }

        setContent {
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
                    viewModel = hiltViewModel(),
                    uiViewModel = hiltViewModel(),
                    windowSizeClass = calculateWindowSizeClass(this),
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
                viewModel.setIsPlaying(isPlaying)
                viewModel.setIsLoading(isLoading)
                viewModel.setPosition(currentPosition)
                viewModel.setMediaItem(currentMediaItem)
                viewModel.setRepeatMode(repeatMode)
                viewModel.setShuffleMode(shuffleModeEnabled)
                viewModel.setDuration(duration)

                viewModel.setHasNext(hasNextMediaItem())
                viewModel.setHasPrevious(hasPreviousMediaItem())

                addListener(listener)
            }
        }
    }

    override fun onStop() {
        super.onStop()

        lifecycleScope.launch {
            requireController().removeListener(listener)
            MediaController.releaseFuture(controllerFuture)
        }
    }

    private suspend fun onControllerEvent(event: EnemyState.ControllerEventState) = when (event) {
        is EnemyState.ControllerEventState.Event -> {
            when (event) {
                is EnemyState.ControllerEventState.Event.Play -> with(requireController()) {
                    when (event) {
                        is EnemyState.ControllerEventState.Event.Play.With -> {
                            setMediaItems(event.items)
                            seekToDefaultPosition(event.index)
                        }
                        else -> Unit
                    }

                    prepare()
                    play()
                }
                is EnemyState.ControllerEventState.Event.Pause -> {
                    requireController().pause()
                }
                is EnemyState.ControllerEventState.Event.Next.First -> {
                    requireController().seekToNextMediaItem()
                }
                is EnemyState.ControllerEventState.Event.Previous.Last -> {
                    requireController().seekToPreviousMediaItem()
                }
                is EnemyState.ControllerEventState.Event.Repeat -> {
                    requireController().repeatMode = event.repeatMode
                }
                is EnemyState.ControllerEventState.Event.Shuffle -> {
                    requireController().shuffleModeEnabled = event.shuffleMode
                }
                is EnemyState.ControllerEventState.Event.Seek -> {
                    requireController().seekTo(event.milliseconds)
                }
                is EnemyState.ControllerEventState.Event.Next.Last -> with(requireController()) {
                    while (hasNextMediaItem()) {
                        val isNotRepeating: Boolean = repeatMode == MediaController.REPEAT_MODE_OFF
                        if (isNotRepeating) seekToNextMediaItem() else break
                    }
                }
                is EnemyState.ControllerEventState.Event.Previous.First -> with(requireController()) {
                    while (hasPreviousMediaItem()) {
                        val isNotRepeating: Boolean = repeatMode == MediaController.REPEAT_MODE_OFF
                        if (isNotRepeating) seekToPreviousMediaItem() else break
                    }
                }
            }

            event.onEvent()
        }
        is EnemyState.ControllerEventState.Default -> Unit
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun requireController(): MediaController = suspendCoroutine {
        controllerFuture.addListener(
            { it.resume(controllerFuture.get()) },
            directExecutor
        )
    }

    private val listener: Player.Listener = object : Player.Listener {

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            viewModel.setIsPlaying(isPlaying)
        }

        override fun onIsLoadingChanged(isLoading: Boolean) {
            super.onIsLoadingChanged(isLoading)
            viewModel.setIsLoading(isLoading)
        }

        override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
            super.onMediaMetadataChanged(mediaMetadata)

            val mediaItem: MediaItem = MediaItem.Builder()
                .setMediaMetadata(mediaMetadata)
                .build()

            viewModel.setMediaItem(mediaItem)
        }

        override fun onRepeatModeChanged(repeatMode: Int) {
            super.onRepeatModeChanged(repeatMode)
            viewModel.setRepeatMode(repeatMode)
        }

        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
            super.onShuffleModeEnabledChanged(shuffleModeEnabled)
            viewModel.setShuffleMode(shuffleModeEnabled)
        }

        override fun onEvents(player: Player, events: Player.Events) {
            super.onEvents(player, events)

            if (events.contains(Player.EVENT_TIMELINE_CHANGED)) {
                viewModel.setDuration(player.duration)
            }

            events.containsAny(
                Player.EVENT_REPEAT_MODE_CHANGED,
                Player.EVENT_SHUFFLE_MODE_ENABLED_CHANGED,
                Player.EVENT_MEDIA_ITEM_TRANSITION,
            ).let { if (!it) return }

            viewModel.setHasNext(player.hasNextMediaItem())
            viewModel.setHasPrevious(player.hasPreviousMediaItem())
        }
    }

    private fun startSetPosition() {
        setPosition = lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val controller: MediaController = requireController()

                while (true) {
                    viewModel.setPosition(controller.currentPosition)
                    delay(timeMillis = 500)
                }
            }
        }
    }

    private fun stopSetPosition() {
        setPosition?.cancel()
    }

    private fun releaseSetPosition() {
        setPosition = null
    }
}
