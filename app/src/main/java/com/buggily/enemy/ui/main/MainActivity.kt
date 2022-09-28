package com.buggily.enemy.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.buggily.enemy.domain.theme.Theme
import com.buggily.enemy.ui.theme.EnemyPalette
import com.buggily.enemy.ui.theme.EnemyTheme
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sessionToken: SessionToken

    @Inject
    lateinit var executor: Executor

    private val viewModel: MainViewModel by viewModels()
    private lateinit var mediaControllerFuture: ListenableFuture<MediaController>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(
            window,
            false
        )

        val insetsController = WindowInsetsControllerCompat(
            window,
            window.decorView
        )

        val mediaState: Flow<MainState.MediaState> = viewModel.state.map { it.mediaState }
        val repeatState: Flow<MainState.MediaState.RepeatState> = mediaState.map { it.repeatState }
        val shuffleState: Flow<MainState.MediaState.ShuffleState> = mediaState.map { it.shuffleState }
        val controllerState: Flow<MainState.MediaState.ControllerState> = mediaState.map { it.controllerState }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch { controllerState.collect { onControllerStateEvent(it) } }
                launch { repeatState.collect { onRepeatStateEvent(it) } }
                launch { shuffleState.collect { onShuffleStateEvent(it) } }
            }
        }

        setContent {
            val theme: Theme by viewModel.theme.collectAsStateWithLifecycle()
            val dynamic: Theme.Dynamic by viewModel.dynamic.collectAsStateWithLifecycle()
            val isSystemInDarkTheme: Boolean = isSystemInDarkTheme()

            val paletteTheme: EnemyPalette.Theme = remember(theme, dynamic) {
                val isDynamic: Boolean = when (dynamic) {
                    Theme.Dynamic.Off -> false
                    else -> true
                }

                when (theme) {
                    is Theme.Default -> EnemyPalette.Theme.Default(
                        isDynamic = isDynamic,
                        isSystemInDarkTheme = isSystemInDarkTheme,
                    )
                    is Theme.Light -> EnemyPalette.Theme.Light(
                        isDynamic = isDynamic,
                    )
                    is Theme.Dark -> EnemyPalette.Theme.Dark(
                        isDynamic = isDynamic,
                    )
                }
            }

            val palette: EnemyPalette = remember(paletteTheme) { EnemyPalette(paletteTheme) }
            val isLight: Boolean = remember(paletteTheme) { palette.isLight }

            LaunchedEffect(isLight) {
                with(insetsController) {
                    isAppearanceLightStatusBars = isLight
                    isAppearanceLightNavigationBars = isLight
                }
            }

            EnemyTheme(palette) {
                MainScreen(
                    viewModel = hiltViewModel(),
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding(),
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()

        mediaControllerFuture = MediaController.Builder(
            this,
            sessionToken
        ).buildAsync()

        lifecycleScope.launch {
            requireMediaController().run {
                viewModel.setIsPlaying(isPlaying)
                viewModel.setItem(currentMediaItem)
                viewModel.setRepeatMode(repeatMode)
                viewModel.setHasNext(hasNextMediaItem())
                viewModel.setHasPrevious(hasPreviousMediaItem())

                addListener(listener)
            }
        }
    }

    override fun onStop() {
        super.onStop()

        lifecycleScope.launch {
            requireMediaController().removeListener(listener)
            MediaController.releaseFuture(mediaControllerFuture)
        }
    }

    private suspend fun onControllerStateEvent(event: MainState.MediaState.ControllerState) = when (event) {
        is MainState.MediaState.ControllerState.Event -> {
            when (event) {
                is MainState.MediaState.ControllerState.Event.Play -> requireMediaController().run {
                    when (event) {
                        is MainState.MediaState.ControllerState.Event.Play.With -> {
                            setMediaItems(event.items)
                            seekToDefaultPosition(event.index)
                        }
                        else -> Unit
                    }

                    prepare()
                    play()
                }
                is MainState.MediaState.ControllerState.Event.Pause -> {
                    requireMediaController().pause()
                }
                is MainState.MediaState.ControllerState.Event.Next -> {
                    requireMediaController().seekToNext()
                }
                is MainState.MediaState.ControllerState.Event.Previous -> {
                    requireMediaController().seekToPrevious()
                }
            }

            event.onEvent()
        }
        is MainState.MediaState.ControllerState.Default -> Unit
    }

    private suspend fun onRepeatStateEvent(event: MainState.MediaState.RepeatState) = when (event) {
        is MainState.MediaState.RepeatState.Event.Set -> {
            requireMediaController().repeatMode = event.repeatMode
            event.onEvent()
        }
        is MainState.MediaState.RepeatState.Default -> Unit
    }

    private suspend fun onShuffleStateEvent(event: MainState.MediaState.ShuffleState) = when (event) {
        is MainState.MediaState.ShuffleState.Event.Set -> {
            requireMediaController().shuffleModeEnabled = event.shuffleMode
            event.onEvent()
        }
        is MainState.MediaState.ShuffleState.Default -> Unit
    }

    private suspend fun requireMediaController(): MediaController = suspendCoroutine {
        mediaControllerFuture.addListener(
            { it.resume(mediaControllerFuture.get()) },
            executor
        )
    }

    private val listener: Player.Listener = object : Player.Listener {

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            super.onIsPlayingChanged(isPlaying)
            viewModel.setIsPlaying(isPlaying)
        }

        override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
            super.onMediaMetadataChanged(mediaMetadata)

            val item: MediaItem = MediaItem.Builder()
                .setMediaMetadata(mediaMetadata)
                .build()

            viewModel.setItem(item)
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

            events.containsAny(
                Player.EVENT_REPEAT_MODE_CHANGED,
                Player.EVENT_SHUFFLE_MODE_ENABLED_CHANGED,
                Player.EVENT_MEDIA_ITEM_TRANSITION,
            ).let { if (!it) return }

            lifecycleScope.launch {
                viewModel.setHasNext(player.hasNextMediaItem())
                viewModel.setHasPrevious(player.hasPreviousMediaItem())
            }
        }
    }
}
