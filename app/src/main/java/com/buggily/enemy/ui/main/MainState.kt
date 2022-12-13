package com.buggily.enemy.ui.main

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.navigation.NavDestination
import com.buggily.enemy.core.model.ext.isNonNull
import com.buggily.enemy.feature.album.AlbumState
import com.buggily.enemy.ui.EnemyDestination

data class MainState(
    val mediaState: MediaState,
    val navigationState: NavigationState,
    val controllerState: ControllerState,
    val albumTrackState: AlbumState.TrackState,
) {

    data class NavigationState(
        val destination: NavDestination?,
        val onDestinationChange: (NavDestination?) -> Unit,
    ) {

        val enemyDestination: EnemyDestination?
            get() = EnemyDestination.get(destination)

        companion object {
            val default: NavigationState
                get() = NavigationState(
                    destination = null,
                    onDestinationChange = {},
                )
        }
    }

    data class ControllerState(
        val playState: PlayState,
        val nextState: NextState,
        val previousState: PreviousState,
        val itemState: ItemState,
    ) {

        val isVisible: Boolean
            get() = itemState.item.isNonNull()

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

        data class ItemState(
            val item: MediaItem?,
        ) {

            val nameText: String
                get() = mediaMetadata?.title?.toString() ?: String()

            val artistText: String
                get() = mediaMetadata?.artist?.toString() ?: String()

            private val mediaMetadata: MediaMetadata?
                get() = item?.mediaMetadata

            companion object {
                val default: ItemState
                    get() = ItemState(
                        item = null,
                    )
            }
        }

        companion object {
            val default: ControllerState
                get() = ControllerState(
                    playState = PlayState.default,
                    nextState = NextState.default,
                    previousState = PreviousState.default,
                    itemState = ItemState.default,
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
        val default: MainState
            get() = MainState(
                navigationState = NavigationState.default,
                controllerState = ControllerState.default,
                mediaState = MediaState.default,
                albumTrackState = AlbumState.TrackState.default,
            )
    }
}
