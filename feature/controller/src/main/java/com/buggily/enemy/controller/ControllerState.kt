package com.buggily.enemy.controller

import androidx.media3.common.MediaItem
import com.buggily.enemy.core.model.ext.isNonNull

data class ControllerState(
    val mediaItem: MediaItem?,
    val playState: PlayState,
    val nextStates: NextStates,
    val previousStates: PreviousStates,
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

    data class NextStates(
        val isEnabled: Boolean,
        val first: NextState.First,
        val last: NextState.Last,
    ) {

        companion object {
            val default: NextStates
                get() = NextStates(
                    isEnabled = false,
                    first = NextState.First.default,
                    last = NextState.Last.default,
                )
        }
    }

    sealed class NextState(
        open val onClick: () -> Unit,
    ) {

        data class First(
            override val onClick: () -> Unit,
        ) : NextState(
            onClick = onClick,
        ) {

            companion object {
                val default: First
                    get() = First(
                        onClick = {},
                    )
            }
        }

        data class Last(
            override val onClick: () -> Unit,
        ) : NextState(
            onClick = onClick,
        ) {

            companion object {
                val default: Last
                    get() = Last(
                        onClick = {},
                    )
            }
        }
    }

    data class PreviousStates(
        val isEnabled: Boolean,
        val first: PreviousState.First,
        val last: PreviousState.Last,
    ) {

        companion object {
            val default: PreviousStates
                get() = PreviousStates(
                    isEnabled = false,
                    first = PreviousState.First.default,
                    last = PreviousState.Last.default,
                )
        }
    }

    sealed class PreviousState(
        open val onClick: () -> Unit,
    ) {

        data class First(
            override val onClick: () -> Unit,
        ) : PreviousState(
            onClick = onClick,
        ) {

            companion object {
                val default: First
                    get() = First(
                        onClick = {},
                    )
            }
        }

        data class Last(
            override val onClick: () -> Unit,
        ) : PreviousState(
            onClick = onClick,
        ) {

            companion object {
                val default: Last
                    get() = Last(
                        onClick = {},
                    )
            }
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
                nextStates = NextStates.default,
                previousStates = PreviousStates.default,
                repeatState = RepeatState.default,
                shuffleState = ShuffleState.default,
            )
    }
}
