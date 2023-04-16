package com.buggily.enemy.controller

import androidx.media3.common.MediaItem
import com.buggily.enemy.core.data.DurationWithMetadata

data class ControllerUiState(
    val mediaItem: MediaItem?,
    val playState: PlayState,
    val nextState: NextState,
    val previousState: PreviousState,
    val repeatState: RepeatState,
    val shuffleState: ShuffleState,
    val seekState: SeekState,
) {

    val isVisible: Boolean
        get() = mediaItem is MediaItem

    data class PlayState(
        val state: State,
        val isPlaying: Boolean,
        val onClick: () -> Unit,
    ) {

        sealed class State {
            object Default : State()
            object Loading : State()
            object Ready : State()
            object Done : State()
        }

        val isEnabled: Boolean
            get() = when (state) {
                is State.Ready -> true
                is State.Default,
                State.Loading,
                State.Done -> false
            }

        companion object {
            val default: PlayState
                get() = PlayState(
                    state = State.Default,
                    isPlaying = false,
                    onClick = {},
                )
        }
    }

    data class NextState(
        val hasNext: Boolean,
        val onClick: () -> Unit,
    ) {

        companion object {
            val default: NextState
                get() = NextState(
                    hasNext = false,
                    onClick = {},
                )
        }
    }

    data class PreviousState(
        val hasPrevious: Boolean,
        val onClick: () -> Unit,
    ) {

        companion object {
            val default: PreviousState
                get() = PreviousState(
                    hasPrevious = false,
                    onClick = {},
                )
        }
    }

    data class RepeatState(
        val mode: Mode,
        val onClick: () -> Unit,
    ) {

        sealed class Mode {

            sealed class On : Mode() {
                object One : On()
                object All : On()
            }

            object Off : Mode()
        }

        companion object {
            val default: RepeatState
                get() = RepeatState(
                    mode = Mode.Off,
                    onClick = {},
                )
        }
    }

    data class ShuffleState(
        val mode: Mode,
        val onClick: () -> Unit,
    ) {

        sealed class Mode {
            object On : Mode()
            object Off : Mode()
        }

        companion object {
            val default: ShuffleState
                get() = ShuffleState(
                    mode = Mode.Off,
                    onClick = {},
                )
        }
    }

    data class SeekState(
        val current: DurationWithMetadata,
        val duration: DurationWithMetadata,
        val onChange: (Float) -> Unit,
        val onChangeFinish: () -> Unit,
    ) {

        val milliseconds: Long
            get() = current.inWholeMilliseconds

        val value: Float
            get() = current.inWholeSeconds.toFloat()

        private val first: Float
            get() = DurationWithMetadata.default.inWholeSeconds.toFloat()

        private val last: Float
            get() = duration.inWholeSeconds.toFloat()

        val range: ClosedFloatingPointRange<Float>
            get() = first..last

        companion object {
            val default: SeekState
                get() = SeekState(
                    current = DurationWithMetadata.default,
                    duration = DurationWithMetadata.default,
                    onChange = {},
                    onChangeFinish = {},
                )
        }
    }

    companion object {
        val default: ControllerUiState
            get() = ControllerUiState(
                mediaItem = null,
                playState = ControllerUiState.PlayState.default,
                nextState = NextState.default,
                previousState = PreviousState.default,
                repeatState = RepeatState.default,
                shuffleState = ShuffleState.default,
                seekState = SeekState.default,
            )
    }
}
