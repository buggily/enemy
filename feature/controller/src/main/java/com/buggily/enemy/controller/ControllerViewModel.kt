package com.buggily.enemy.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
import com.buggily.core.domain.GetUiDuration
import com.buggily.enemy.domain.controller.Next
import com.buggily.enemy.domain.controller.Pause
import com.buggily.enemy.domain.controller.Play
import com.buggily.enemy.domain.controller.Previous
import com.buggily.enemy.domain.controller.Repeat
import com.buggily.enemy.domain.controller.Seek
import com.buggily.enemy.domain.controller.Shuffle
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
    private val getUiDuration: GetUiDuration,
) : ViewModel() {

    private val _uiState: MutableStateFlow<ControllerUiState>
    val uiState: StateFlow<ControllerUiState> get() = _uiState

    val isPlaying: StateFlow<Boolean>

    init {
        ControllerUiState.default.copy(
            playState = ControllerUiState.PlayState.default.copy(
                onClick = ::onPlayClick,
            ),
            nextState = ControllerUiState.NextState.default.copy(
                onClick = ::onNextClick,
            ),
            previousState = ControllerUiState.PreviousState.default.copy(
                onClick = ::onPreviousClick,
            ),
            repeatState = ControllerUiState.RepeatState.default.copy(
                onClick = ::onRepeatClick,
            ),
            shuffleState = ControllerUiState.ShuffleState.default.copy(
                onClick = ::onShuffleClick,
            ),
            seekState = ControllerUiState.SeekState.default.copy(
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
            initialValue = ControllerUiState.PlayState.default.isPlaying,
        )
    }

    fun setMediaItem(mediaItem: MediaItem?) = _uiState.update {
        it.copy(mediaItem = mediaItem)
    }

    fun setPosition(position: Long) = onSeekChange(
        duration = position.toDuration(DurationUnit.MILLISECONDS),
    )

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

        it.copy(seekState = it.seekState.copy(duration = getUiDuration(milliseconds)))
    }

    private fun onSeekChange(seconds: Float) = onSeekChange(
        duration = seconds.toLong().toDuration(DurationUnit.SECONDS),
    )

    private fun onSeekChange(duration: Duration) = _uiState.update {
        it.copy(seekState = it.seekState.copy(current = getUiDuration(duration)))
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
        val shuffleMode = when (uiState.value.shuffleState.mode) {
            ControllerUiState.ShuffleState.Mode.Off -> true
            ControllerUiState.ShuffleState.Mode.On -> false
        }

        shuffle(shuffleMode)
    }

    private fun onSeekChangeFinish() {
        seek(uiState.value.seekState.milliseconds)
    }
}
