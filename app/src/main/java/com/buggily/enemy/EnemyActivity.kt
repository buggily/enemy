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
class EnemyActivity : ComponentActivity() {

    @Inject
    lateinit var sessionToken: SessionToken

    @Inject
    @DirectExecutorQualifier
    lateinit var directExecutor: Executor

    private val viewModel: EnemyViewModel by viewModels()
    private lateinit var mediaControllerFuture: ListenableFuture<MediaController>

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

        val mediaState: Flow<EnemyState.MediaState> = viewModel.state.map {
            it.mediaState
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mediaState.collect { onMediaState(it) }
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
                    windowSizeClass = calculateWindowSizeClass(this),
                    modifier = Modifier.fillMaxSize(),
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
            with(requireMediaController()) {
                viewModel.setIsPlaying(isPlaying)
                viewModel.setMediaItem(currentMediaItem)
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

    private suspend fun onMediaState(event: EnemyState.MediaState) = when (event) {
        is EnemyState.MediaState.Event -> {
            when (event) {
                is EnemyState.MediaState.Event.Play -> with(requireMediaController()) {
                    when (event) {
                        is EnemyState.MediaState.Event.Play.With -> {
                            setMediaItems(event.items)
                            seekToDefaultPosition(event.index)
                        }
                        else -> Unit
                    }

                    prepare()
                    play()
                }
                is EnemyState.MediaState.Event.Pause -> {
                    requireMediaController().pause()
                }
                is EnemyState.MediaState.Event.Next -> {
                    requireMediaController().seekToNext()
                }
                is EnemyState.MediaState.Event.Previous -> {
                    requireMediaController().seekToPrevious()
                }
                is EnemyState.MediaState.Event.Repeat -> {
                    requireMediaController().repeatMode = event.repeatMode
                }
                is EnemyState.MediaState.Event.Shuffle -> {
                    requireMediaController().shuffleModeEnabled = event.shuffleMode
                }
            }

            event.onEvent()
        }
        is EnemyState.MediaState.Default -> Unit
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun requireMediaController(): MediaController = suspendCoroutine {
        mediaControllerFuture.addListener(
            { it.resume(mediaControllerFuture.get()) },
            directExecutor
        )
    }

    private val listener: Player.Listener by lazy {

        object : Player.Listener {

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                viewModel.setIsPlaying(isPlaying)
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

            override fun onIsLoadingChanged(isLoading: Boolean) {
                super.onIsLoadingChanged(isLoading)
                viewModel.setIsLoading(isLoading)
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
}
