package com.buggily.enemy.ui

import androidx.media3.common.MediaItem
import com.buggily.enemy.controller.ControllerState
import com.buggily.enemy.feature.album.AlbumState
import com.buggily.enemy.tracks.TracksState

data class EnemyState(
    val mediaState: MediaState,
    val controllerState: ControllerState,
    val albumTrackState: AlbumState.TrackState,
    val tracksTrackState: TracksState.TrackState,
) {

    sealed class MediaState {

            object Default : MediaState()

            sealed class Event(
                open val onEvent: () -> Unit,
            ) : MediaState() {

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
                mediaState = MediaState.Default,
                controllerState = ControllerState.default,
                albumTrackState = AlbumState.TrackState.default,
                tracksTrackState = TracksState.TrackState.default,
            )
    }
}
