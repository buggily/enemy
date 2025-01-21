package com.buggily.enemy

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.buggily.enemy.controller.ControllerViewModel
import com.buggily.enemy.core.controller.ControllerEvent
import com.buggily.enemy.core.controller.ControllerOrchestratable
import com.buggily.enemy.core.navigation.NavigationArgs
import com.buggily.enemy.core.navigation.NavigationOrchestratable
import com.buggily.enemy.core.ui.GlobalUiViewModel
import com.buggily.enemy.core.ui.theme.EnemyPalette
import com.buggily.enemy.core.ui.theme.EnemyTheme
import com.buggily.enemy.core.ui.theme.to
import com.buggily.enemy.di.DirectExecutorQualifier
import com.buggily.enemy.domain.theme.ThemeUi
import com.buggily.enemy.ui.EnemyApp
import com.buggily.enemy.ui.EnemyAppState
import com.buggily.enemy.ui.EnemyViewModel
import com.buggily.enemy.ui.rememberEnemyAppState
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import javax.inject.Inject
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.time.Duration

@AndroidEntryPoint
class EnemyActivity : FragmentActivity() {

    private val viewModel: EnemyViewModel by viewModels()
    private val globalUiViewModel: GlobalUiViewModel by viewModels()
    private val controllerViewModel: ControllerViewModel by viewModels()

    private var setPosition: Job? = null
    private var controller: MediaController? = null

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
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    controllerViewModel.isPlaying.collect {
                        if (it) startSetPosition() else stopSetPosition()
                    }
                }

                launch {
                    controllerOrchestrator.event.collect {
                        onControllerEvent(it)
                    }
                }
            }
        }

        setContent {
            val theme: ThemeUi by viewModel.theme.collectAsStateWithLifecycle()

            val navController: NavHostController = rememberNavController()
            val windowSizeClass: WindowSizeClass = calculateWindowSizeClass(this)

            val appState: EnemyAppState = rememberEnemyAppState(
                navController = navController,
                windowSizeClass = windowSizeClass,
            )

            LaunchedEffect(navController) {
                navController.removeOnDestinationChangedListener(onDestinationChangedListener)
                navController.addOnDestinationChangedListener(onDestinationChangedListener)
            }

            LaunchedEffect(navController, navigationOrchestrator) {
                navigationOrchestrator.event.flowWithLifecycle(
                    lifecycle = lifecycle,
                    minActiveState = Lifecycle.State.RESUMED,
                ).collect {
                    when (val args: NavigationArgs = it.args) {
                        is NavigationArgs.Route.WithOptions -> navController.navigate(
                            route = args.route,
                            builder = args.builder,
                        )

                        is NavigationArgs.Route.WithoutOptions -> navController.navigate(
                            route = args.route,
                        )

                        is NavigationArgs.Back.WithOptions -> navController.popBackStack(
                            route = args.route,
                            inclusive = true,
                        )

                        is NavigationArgs.Back.WithoutOptions -> navController.popBackStack()
                    }
                }
            }

            val isSystemInDarkTheme: Boolean = isSystemInDarkTheme()
            val paletteTheme: EnemyPalette.Theme = remember(isSystemInDarkTheme, theme) {
                val isDynamic: Boolean = theme.dynamic is ThemeUi.Dynamic.On

                theme.scheme.to(
                    isDynamic = isDynamic,
                    isSystemInDarkTheme = isSystemInDarkTheme,
                )
            }

            val palette: EnemyPalette = remember(paletteTheme) {
                EnemyPalette(paletteTheme)
            }

            EnemyTheme(palette) {
                EnemyApp(
                    appState = appState,
                    viewModel = hiltViewModel(this),
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
                controllerViewModel.setIsPlaying(isPlaying)

                controllerViewModel.setDuration(duration)
                controllerViewModel.setPosition(currentPosition)

                controllerViewModel.setRepeatMode(repeatMode)
                controllerViewModel.setShuffleMode(shuffleModeEnabled)
                controllerViewModel.setAvailableCommands(availableCommands)

                removeListener(controllerListener)
                addListener(controllerListener)
            }
        }
    }

    override fun onStop() {
        super.onStop()

        lifecycleScope.launch {
            requireController().removeListener(controllerListener)
            MediaController.releaseFuture(controllerFuture)
            controller = null
        }

        stopSetPosition()
    }

    override fun onDestroy() {
        super.onDestroy()
        destroySetPosition()
    }

    private suspend fun onControllerEvent(event: ControllerEvent) {
        when (event) {
            is ControllerEvent.Play -> with(requireController()) {
                when (event) {
                    is ControllerEvent.Play.WithMany -> setMediaItems(
                        event.items,
                        event.index,
                        Duration.ZERO.inWholeMilliseconds,
                    )

                    is ControllerEvent.Play.WithOne -> setMediaItem(
                        event.item,
                        Duration.ZERO.inWholeMilliseconds,
                    )

                    is ControllerEvent.Play.Without -> Unit
                }

                prepare()
                play()
            }

            is ControllerEvent.Pause -> {
                requireController().pause()
            }

            is ControllerEvent.Next -> {
                requireController().seekToNext()
                setPosition()
            }

            is ControllerEvent.Previous -> {
                requireController().seekToPrevious()
                setPosition()
            }

            is ControllerEvent.Repeat -> {
                requireController().repeatMode = event.repeatMode
            }

            is ControllerEvent.Shuffle -> {
                requireController().shuffleModeEnabled = event.shuffleMode
            }

            is ControllerEvent.Seek -> {
                requireController().seekTo(event.milliseconds)
            }
        }
    }

    private suspend fun requireController(): MediaController = controller ?: suspendCoroutine {
        controllerFuture.addListener(
            getRequireControllerListener(it),
            executor,
        )
    }.also { controller = it }

    private fun getRequireControllerListener(
        continuation: Continuation<MediaController>,
    ): () -> Unit = {
        try {
            continuation.resume(controllerFuture.get())
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }

    private val onDestinationChangedListener: NavController.OnDestinationChangedListener =
        NavController.OnDestinationChangedListener { _, it: NavDestination, _ ->
            globalUiViewModel.onDestinationChange(it)
        }

    private val controllerListener: Player.Listener by lazy {
        object : Player.Listener {

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
                controllerViewModel.setIsPlaying(isPlaying)
            }

            override fun onMediaItemTransition(
                mediaItem: MediaItem?,
                reason: Int,
            ) {
                super.onMediaItemTransition(mediaItem, reason)
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

            override fun onAvailableCommandsChanged(availableCommands: Player.Commands) {
                super.onAvailableCommandsChanged(availableCommands)
                controllerViewModel.setAvailableCommands(availableCommands)
            }

            override fun onEvents(player: Player, events: Player.Events) {
                super.onEvents(player, events)

                if (!events.contains(Player.EVENT_PLAYBACK_STATE_CHANGED)) return
                controllerViewModel.setDuration(player.duration)
            }
        }
    }

    private fun startSetPosition() {
        setPosition = lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                while (true) {
                    setPosition()
                    delay(timeMillis = 1000)
                }
            }
        }
    }

    private fun stopSetPosition() {
        setPosition?.cancel()
    }

    private fun destroySetPosition() {
        stopSetPosition()
        setPosition = null
    }

    private suspend fun setPosition() = with(requireController()) {
        controllerViewModel.setPosition(currentPosition)
    }
}
