package com.buggily.enemy

import androidx.media3.common.MediaItem
import com.buggily.enemy.core.model.ext.isNonNull
import com.buggily.enemy.feature.album.AlbumState

data class EnemyState(
    val mediaState: MediaState,
    val controllerState: ControllerState,
    val shuffleState: ShuffleState,
    val repeatState: RepeatState,
    val albumTrackState: AlbumState.TrackState,
) {

    data class ControllerState(
        val item: MediaItem?,
        val isExpanded: Boolean,
        val playState: PlayState,
        val nextState: NextState,
        val previousState: PreviousState,
        val onClick: () -> Unit,
    ) {

        val isVisible: Boolean
            get() = item.isNonNull()

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

        companion object {
            val default: ControllerState
                get() = ControllerState(
                    item = null,
                    isExpanded = false,
                    playState = PlayState.default,
                    nextState = NextState.default,
                    previousState = PreviousState.default,
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

    data class MediaState(
        val controllerState: ControllerState,
        val repeatState: RepeatState,
        val shuffleState: ShuffleState,
    ) {

        sealed class ControllerState {

            object Default : ControllerState()

            sealed class Event(
                val onEvent: () -> Unit,
            ) : ControllerState() {

                sealed class Play(
                    open val onPlay: () -> Unit,
                ) : Event(
                    onEvent = onPlay,
                ) {

                    data class With(
                        val index: Int,
                        val items: List<MediaItem>,
                        override val onPlay: () -> Unit,
                    ) : Play(
                        onPlay = onPlay,
                    )

                    data class Without(
                        override val onPlay: () -> Unit,
                    ) : Play(
                        onPlay = onPlay,
                    )
                }

                data class Pause(
                    val onPause: () -> Unit,
                ) : Event(
                    onEvent = onPause,
                )

                data class Previous(
                    val onPrevious: () -> Unit,
                ) : Event(
                    onEvent = onPrevious,
                )

                data class Next(
                    val onNext: () -> Unit,
                ) : Event(
                    onEvent = onNext,
                )
            }
        }

        sealed class RepeatState {

            object Default : RepeatState()

            sealed class Event(
                val onEvent: () -> Unit,
            ) : RepeatState() {

                data class Set(
                    val repeatMode: Int,
                    val onRepeat: () -> Unit,
                ) : Event(
                    onEvent = onRepeat,
                )
            }
        }

        sealed class ShuffleState {

            object Default : ShuffleState()

            sealed class Event(
                val onEvent: () -> Unit,
            ) : ShuffleState() {

                data class Set(
                    val shuffleMode: Boolean,
                    val onShuffle: () -> Unit,
                ) : Event(
                    onEvent = onShuffle,
                )
            }
        }

        companion object {
            val default: MediaState
                get() = MediaState(
                    controllerState = ControllerState.Default,
                    repeatState = RepeatState.Default,
                    shuffleState = ShuffleState.Default,
                )
        }
    }

    companion object {
        val default: EnemyState
            get() = EnemyState(
                mediaState = MediaState.default,
                controllerState = ControllerState.default,
                repeatState = RepeatState.default,
                shuffleState = ShuffleState.default,
                albumTrackState = AlbumState.TrackState.default,
            )
    }
}
