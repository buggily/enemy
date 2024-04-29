package com.buggily.enemy.controller

import androidx.media3.common.MediaItem
import com.buggily.enemy.core.ext.isPositive
import kotlin.time.Duration as KDuration

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
        val current: Duration,
        val duration: Duration,
        val onChange: (Float) -> Unit,
        val onChangeFinish: () -> Unit,
    ) {

        private val first: Float = KDuration.ZERO.inWholeSeconds.toFloat()
        private val last: Float = duration.duration.inWholeSeconds.toFloat()
        val range: ClosedFloatingPointRange<Float> = first..last

        val milliseconds: Long
            get() = current.duration.inWholeMilliseconds

        val value: Float
            get() = current.duration.inWholeSeconds.toFloat()

        val progress: Float
            get() = last.takeIf { it.isPositive }?.let { value / it } ?: first

        data class Duration(
            val duration: KDuration,
            val text: String,
        )
    }
}
