package com.buggily.enemy.controller

import androidx.media3.common.MediaItem
import com.buggily.enemy.core.data.DurationWithMetadata
import com.buggily.enemy.core.ext.isPositive

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

        sealed interface State {
            data object Default : State
            data object Loading : State
            data object Ready : State
            data object Done : State
        }

        val isEnabled: Boolean
            get() = when (state) {
                is State.Ready -> true
                is State.Default,
                is State.Loading,
                is State.Done -> false
            }
    }

    data class NextState(
        val hasNext: Boolean,
        val onClick: () -> Unit,
    )

    data class PreviousState(
        val hasPrevious: Boolean,
        val onClick: () -> Unit,
    )

    data class RepeatState(
        val mode: Mode,
        val onClick: () -> Unit,
    ) {

        sealed interface Mode {

            sealed interface On : Mode {
                data object One : On
                data object All : On
            }

            data object Off : Mode
        }
    }

    data class ShuffleState(
        val mode: Mode,
        val onClick: () -> Unit,
    ) {

        sealed interface Mode {
            data object On : Mode
            data object Off : Mode
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

        val progress: Float
            get() = last.takeIf { it.isPositive }?.let { value / it } ?: first

        private val first: Float
            get() = DurationWithMetadata.ZERO.inWholeSeconds.toFloat()

        private val last: Float
            get() = duration.inWholeSeconds.toFloat()

        val range: ClosedFloatingPointRange<Float>
            get() = first..last
    }
}
