package com.buggily.enemy.controller

import androidx.media3.common.MediaItem
import com.buggily.enemy.core.model.ext.isNonNull

data class ControllerState(
    val mediaItem: MediaItem?,
    val playState: PlayState,
    val nextState: NextState,
    val previousState: PreviousState,
    val repeatState: RepeatState,
    val shuffleState: ShuffleState,
) {

    val isVisible: Boolean
        get() = mediaItem.isNonNull()

    data class PlayState(
        val isPlaying: Boolean,
        val isEnabled: Boolean,
        val onClick: () -> Unit,
    ) {

        companion object {
            val default: PlayState
                get() = PlayState(
                    isPlaying = false,
                    isEnabled = false,
                    onClick = {},
                )
        }
    }

    data class NextState(
        val isEnabled: Boolean,
        val onClick: () -> Unit,
    ) {

        companion object {
            val default: NextState
                get() = NextState(
                    isEnabled = false,
                    onClick = {},
                )
        }
    }

    data class PreviousState(
        val isEnabled: Boolean,
        val onClick: () -> Unit,
    ) {

        companion object {
            val default: PreviousState
                get() = PreviousState(
                    isEnabled = false,
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

    companion object {
        val default: ControllerState
            get() = ControllerState(
                mediaItem = null,
                playState = PlayState.default,
                nextState = NextState.default,
                previousState = PreviousState.default,
                repeatState = RepeatState.default,
                shuffleState = ShuffleState.default,
            )
    }
}
