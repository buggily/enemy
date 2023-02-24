package com.buggily.enemy.ui

import androidx.media3.common.MediaItem
import com.buggily.enemy.controller.ControllerState
import com.buggily.enemy.feature.album.AlbumState
import com.buggily.enemy.tracks.TracksState

data class EnemyState(
    val controllerState: ControllerState,
    val controllerEventState: ControllerEventState,
    val albumTrackState: AlbumState.TrackState,
    val tracksTrackState: TracksState.TrackState,
) {

    sealed class ControllerEventState {

        object Default : ControllerEventState()

        sealed class Event(
            open val onEvent: () -> Unit,
        ) : ControllerEventState() {

            sealed class Play(
                override val onEvent: () -> Unit,
            ) : Event(
                onEvent = onEvent,
            ) {

                data class With(
                    val index: Int,
                    val items: List<MediaItem>,
                    override val onEvent: () -> Unit,
                ) : Play(
                    onEvent = onEvent,
                )

                data class Without(
                    override val onEvent: () -> Unit,
                ) : Play(
                    onEvent = onEvent,
                )
            }

            data class Pause(
                override val onEvent: () -> Unit,
            ) : Event(
                onEvent = onEvent,
            )

            sealed class Previous(
                override val onEvent: () -> Unit,
            ) : Event(
                onEvent = onEvent,
            ) {

                data class First(
                    override val onEvent: () -> Unit,
                ) : Previous(
                    onEvent = onEvent,
                )

                data class Last(
                    override val onEvent: () -> Unit,
                ) : Previous(
                    onEvent = onEvent,
                )
            }

            sealed class Next(
                override val onEvent: () -> Unit,
            ) : Event(
                onEvent = onEvent,
            ) {

                data class First(
                    override val onEvent: () -> Unit,
                ) : Next(
                    onEvent = onEvent,
                )

                data class Last(
                    override val onEvent: () -> Unit,
                ) : Next(
                    onEvent = onEvent,
                )
            }

            data class Repeat(
                override val onEvent: () -> Unit,
                val repeatMode: Int,
            ) : Event(
                onEvent = onEvent
            )

            data class Shuffle(
                override val onEvent: () -> Unit,
                val shuffleMode: Boolean,
            ) : Event(
                onEvent = onEvent,
            )

            data class Seek(
                override val onEvent: () -> Unit,
                val milliseconds: Long,
            ) : Event(
                onEvent = onEvent,
            )
        }
    }

    companion object {
        val default: EnemyState
            get() = EnemyState(
                controllerState = ControllerState.default,
                controllerEventState = ControllerEventState.Default,
                albumTrackState = AlbumState.TrackState.default,
                tracksTrackState = TracksState.TrackState.default,
            )
    }
}
