package com.buggily.enemy.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
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
            mediaItem = null,
            playState = ControllerUiState.PlayState(
                state = ControllerUiState.PlayState.State.Default,
                isPlaying = false,
                onClick = ::onPlayClick,
            ),
            nextState = ControllerUiState.NextState(
                hasNext = false,
                onClick = ::onNextClick,
            ),
            previousState = ControllerUiState.PreviousState(
                hasPrevious = false,
                onClick = ::onPreviousClick,
            ),
            repeatState = ControllerUiState.RepeatState(
                mode = ControllerUiState.RepeatState.Mode.Off,
                onClick = ::onRepeatClick,
            ),
            shuffleState = ControllerUiState.ShuffleState(
                mode = ControllerUiState.ShuffleState.Mode.Off,
                onClick = ::onShuffleClick,
            ),
            seekState = ControllerUiState.SeekState(
                current = duration,
                duration = duration,
                onChange = ::onSeekChange,
                onChangeFinish = ::onSeekChangeFinish,
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

        isEmpty = uiState.map { it.mediaItem == null && !it.playState.isPlaying }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = false,
        )
    }

    fun setMediaItem(mediaItem: MediaItem?) = _uiState.update {
        it.copy(mediaItem = mediaItem)
    }

    fun setPosition(position: Long) {
        if (!isSeeking) onSeekChange(duration = position.toDuration(DurationUnit.MILLISECONDS))
    }

    fun setPlaybackState(playbackState: Int) = _uiState.update {
        val state: ControllerUiState.PlayState.State = when (playbackState) {
            MediaController.STATE_IDLE -> ControllerUiState.PlayState.State.Default
            MediaController.STATE_BUFFERING -> ControllerUiState.PlayState.State.Loading
            MediaController.STATE_READY -> ControllerUiState.PlayState.State.Ready
            MediaController.STATE_ENDED -> ControllerUiState.PlayState.State.Done
            else -> ControllerUiState.PlayState.State.Default
        }

        it.copy(playState = it.playState.copy(state = state))
    }

    fun setIsPlaying(isPlaying: Boolean) = _uiState.update {
        it.copy(playState = it.playState.copy(isPlaying = isPlaying))
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

    fun setHasNext(hasNext: Boolean) = _uiState.update {
        it.copy(nextState = it.nextState.copy(hasNext = hasNext))
    }

    fun setHasPrevious(hasPrevious: Boolean) = _uiState.update {
        it.copy(previousState = it.previousState.copy(hasPrevious = hasPrevious))
    }

    fun setDuration(milliseconds: Long) = _uiState.update {
        val start: Long = Duration.ZERO.inWholeMilliseconds
        val endExclusive: Long = Duration.INFINITE.inWholeMilliseconds
        val range: LongRange = start until endExclusive
        if (milliseconds !in range) return@update it

        val duration = ControllerUiState.SeekState.Duration(
            text = getDurationText(milliseconds),
            duration = getDuration(milliseconds),
        )

        it.copy(seekState = it.seekState.copy(duration = duration))
    }

    fun onEmpty() = navigateBackFromController()

    private fun onSeekChange(seconds: Float) {
        isSeeking = true

        getDuration(
            duration = seconds.toLong(),
            unit = DurationUnit.SECONDS,
        ).let { onSeekChange(it) }
    }

    private fun onSeekChange(duration: Duration) = _uiState.update {
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

    private fun onSeekChangeFinish() {
        seek(uiState.value.seekState.milliseconds)
        isSeeking = false
    }
}
