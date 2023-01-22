package com.buggily.enemy.ui

import androidx.media3.common.MediaItem
import com.buggily.enemy.controller.ControllerState
import com.buggily.enemy.feature.album.AlbumState

data class EnemyState(
    val mediaState: MediaState,
    val controllerState: ControllerState,
    val albumTrackState: AlbumState.TrackState,
) {

    sealed class MediaState {

            object Default : MediaState()

            sealed class Event(
                val onEvent: () -> Unit,
            ) : MediaState() {

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

                data class Repeat(
                    val repeatMode: Int,
                    val onRepeat: () -> Unit,
                ) : Event(
                    onEvent = onRepeat,
                )

                data class Shuffle(
                    val shuffleMode: Boolean,
                    val onShuffle: () -> Unit,
                ) : Event(
                    onEvent = onShuffle,
                )
            }
    }

    companion object {
        val default: EnemyState
            get() = EnemyState(
                mediaState = MediaState.Default,
                controllerState = ControllerState.default,
                albumTrackState = AlbumState.TrackState.default,
            )
    }
}
