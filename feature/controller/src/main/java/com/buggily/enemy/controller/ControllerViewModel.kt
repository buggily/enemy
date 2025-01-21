package com.buggily.enemy.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import com.buggily.enemy.core.domain.GetDuration
import com.buggily.enemy.core.domain.GetDurationText
import com.buggily.enemy.domain.controller.Next
import com.buggily.enemy.domain.controller.Pause
import com.buggily.enemy.domain.controller.Play
import com.buggily.enemy.domain.controller.Previous
import com.buggily.enemy.domain.controller.Repeat
import com.buggily.enemy.domain.controller.Seek
import com.buggily.enemy.domain.controller.Shuffle
import com.buggily.enemy.domain.navigation.NavigateBackFromController
import com.buggily.enemy.domain.navigation.NavigateToAlbum
import com.buggily.enemy.domain.track.GetTrackById
import com.buggily.enemy.domain.track.TrackUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@HiltViewModel
class ControllerViewModel @Inject constructor(
    private val play: Play,
    private val pause: Pause,
    private val next: Next,
    private val previous: Previous,
    private val repeat: Repeat,
    private val shuffle: Shuffle,
    private val seek: Seek,
    private val getTrackById: GetTrackById,
    private val navigateToAlbum: NavigateToAlbum,
    private val navigateBackFromController: NavigateBackFromController,
    private val getDurationText: GetDurationText,
    private val getDuration: GetDuration,
) : ViewModel() {

    private val _uiState: MutableStateFlow<ControllerUiState>
    val uiState: StateFlow<ControllerUiState> get() = _uiState

    val isEmpty: StateFlow<Boolean>
    val isPlaying: StateFlow<Boolean>
    private var isSeeking = false

    init {
        val duration = ControllerUiState.SeekState.Duration(
            duration = Duration.ZERO,
            text = getDurationText(Duration.ZERO),
        )

        ControllerUiState(
            mediaState = ControllerUiState.MediaState(
                mediaItem = null,
                onClick = ::onMediaItemClick,
            ),
            playState = ControllerUiState.PlayState(
                isEnabled = false,
                isPlaying = false,
                onClick = ::onPlayClick,
            ),
            nextState = ControllerUiState.NextState(
                isEnabled = false,
                onClick = ::onNextClick,
            ),
            previousState = ControllerUiState.PreviousState(
                isEnabled = false,
                onClick = ::onPreviousClick,
            ),
            repeatState = ControllerUiState.RepeatState(
                mode = ControllerUiState.RepeatState.Mode.Off,
                onClick = ::onRepeatClick,
                isEnabled = false,
            ),
            shuffleState = ControllerUiState.ShuffleState(
                mode = ControllerUiState.ShuffleState.Mode.Off,
                onClick = ::onShuffleClick,
                isEnabled = false,
            ),
            seekState = ControllerUiState.SeekState(
                current = duration,
                duration = duration,
                onChange = ::onSeekStarted,
                onChangeFinish = ::onSeekFinished,
            ),
        ).let { _uiState = MutableStateFlow(it) }

        val playState: Flow<ControllerUiState.PlayState> = uiState.map {
            it.playState
        }

        isPlaying = playState.map { it.isPlaying }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = false,
        )

        isEmpty = uiState.map { !it.mediaState.hasMediaItem && !it.playState.isPlaying }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = false,
        )
    }

    fun setIsPlaying(isPlaying: Boolean) = _uiState.update {
        it.copy(playState = it.playState.copy(isPlaying = isPlaying))
    }

    fun setMediaItem(mediaItem: MediaItem?) = _uiState.update {
        it.copy(mediaState = it.mediaState.copy(mediaItem = mediaItem))
    }

    fun setDuration(milliseconds: Long) {
        val start: Long = Duration.ZERO.inWholeMilliseconds
        val end: Long = Duration.INFINITE.inWholeMilliseconds

        val range: LongRange = start until end
        if (milliseconds !in range) return

        _uiState.update {
            val duration = ControllerUiState.SeekState.Duration(
                text = getDurationText(milliseconds),
                duration = getDuration(milliseconds),
            )

            it.copy(seekState = it.seekState.copy(duration = duration))
        }
    }

    fun setPosition(position: Long) {
        if (!isSeeking) onSeekStarted(duration = position.toDuration(DurationUnit.MILLISECONDS))
    }

    fun setRepeatMode(repeatMode: Int) = _uiState.update {
        val mode: ControllerUiState.RepeatState.Mode = when (repeatMode) {
            MediaController.REPEAT_MODE_ONE -> ControllerUiState.RepeatState.Mode.On.One
            MediaController.REPEAT_MODE_ALL -> ControllerUiState.RepeatState.Mode.On.All
            else -> ControllerUiState.RepeatState.Mode.Off
        }

        it.copy(repeatState = it.repeatState.copy(mode = mode))
    }

    fun setShuffleMode(shuffleMode: Boolean) = _uiState.update {
        val mode: ControllerUiState.ShuffleState.Mode = when (shuffleMode) {
            true -> ControllerUiState.ShuffleState.Mode.On
            false -> ControllerUiState.ShuffleState.Mode.Off
        }

        it.copy(shuffleState = it.shuffleState.copy(mode = mode))
    }

    fun setAvailableCommands(availableCommands: Player.Commands) = _uiState.update {
        val isPlayEnabled: Boolean = Player.COMMAND_PLAY_PAUSE in availableCommands
        val isNextEnabled: Boolean = Player.COMMAND_SEEK_TO_NEXT in availableCommands
        val isPreviousEnabled: Boolean = Player.COMMAND_SEEK_TO_PREVIOUS in availableCommands
        val isRepeatEnabled: Boolean = Player.COMMAND_SET_REPEAT_MODE in availableCommands
        val isShuffleEnabled: Boolean = Player.COMMAND_SET_SHUFFLE_MODE in availableCommands

        val playState: ControllerUiState.PlayState = it.playState.copy(
            isEnabled = isPlayEnabled,
        )

        val nextState: ControllerUiState.NextState = it.nextState.copy(
            isEnabled = isNextEnabled,
        )

        val previousState: ControllerUiState.PreviousState = it.previousState.copy(
            isEnabled = isPreviousEnabled,
        )

        val repeatState: ControllerUiState.RepeatState = it.repeatState.copy(
            isEnabled = isRepeatEnabled,
        )

        val shuffleState: ControllerUiState.ShuffleState = it.shuffleState.copy(
            isEnabled = isShuffleEnabled,
        )

        it.copy(
            playState = playState,
            nextState = nextState,
            previousState = previousState,
            repeatState = repeatState,
            shuffleState = shuffleState,
        )
    }

    fun onEmpty() = navigateBackFromController()

    private fun onSeekStarted(seconds: Float) {
        isSeeking = true

        getDuration(
            duration = seconds.toLong(),
            unit = DurationUnit.SECONDS,
        ).let { onSeekStarted(it) }
    }

    private fun onSeekStarted(duration: Duration) = _uiState.update {
        val current = ControllerUiState.SeekState.Duration(
            duration = duration,
            text = getDurationText(duration),
        )

        it.copy(seekState = it.seekState.copy(current = current))
    }

    private fun onPlayClick() {
        if (uiState.value.playState.isPlaying) pause() else play()
    }

    private fun onPreviousClick() = previous()
    private fun onNextClick() = next()

    private fun onRepeatClick() {
        val repeatMode: Int = when (uiState.value.repeatState.mode) {
            ControllerUiState.RepeatState.Mode.Off -> MediaController.REPEAT_MODE_ONE
            ControllerUiState.RepeatState.Mode.On.One -> MediaController.REPEAT_MODE_ALL
            ControllerUiState.RepeatState.Mode.On.All -> MediaController.REPEAT_MODE_OFF
        }

        repeat(repeatMode)
    }

    private fun onShuffleClick() {
        val shuffleMode: Boolean = when (uiState.value.shuffleState.mode) {
            ControllerUiState.ShuffleState.Mode.Off -> true
            ControllerUiState.ShuffleState.Mode.On -> false
        }

        shuffle(shuffleMode)
    }

    private fun onSeekFinished() {
        seek(uiState.value.seekState.milliseconds)
        isSeeking = false
    }

    private fun onMediaItemClick() = viewModelScope.launch {
        val mediaId: Long = uiState.value.mediaState.mediaId ?: return@launch
        val track: TrackUi = getTrackById(mediaId) ?: return@launch
        val albumMediaId: Long = track.album.id

        navigateToAlbum(albumMediaId)
    }
}
