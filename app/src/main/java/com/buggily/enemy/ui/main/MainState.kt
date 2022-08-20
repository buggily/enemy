package com.buggily.enemy.ui.main

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.navigation.NavDestination
import com.buggily.enemy.ui.EnemyDestination

data class MainState(
    val navigationState: NavigationState,
    val collapsableState: CollapsableState,
    val controllerState: ControllerState,
    val mediaState: MediaState,
) {

    data class NavigationState(
        private val destination: NavDestination?,
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

    data class CollapsableState(
        val isCollapsable: Boolean,
        val onCollapsableClick: () -> Unit,
        val searchState: SearchState,
        val repeatState: RepeatState,
        val shuffleState: ShuffleState,
    ) {

        data class SearchState(
            val isSearch: Boolean,
            val onSearchClick: () -> Unit,
        ) {

            companion object {
                val default: SearchState
                    get() = SearchState(
                        isSearch = false,
                        onSearchClick = {},
                    )
            }
        }

        data class RepeatState(
            val isRepeat: Boolean,
            val onRepeatClick: () -> Unit,
        ) {

            companion object {
                val default: RepeatState
                    get() = RepeatState(
                        isRepeat = false,
                        onRepeatClick = {},
                    )
            }
        }

        data class ShuffleState(
            val isShuffle: Boolean,
            val onShuffleClick: () -> Unit,
        ) {

            companion object {
                val default: ShuffleState
                    get() = ShuffleState(
                        isShuffle = false,
                        onShuffleClick = {},
                    )
            }
        }

        companion object {
            val default: CollapsableState
                get() = CollapsableState(
                    isCollapsable = false,
                    onCollapsableClick = {},
                    searchState = SearchState.default,
                    repeatState = RepeatState.default,
                    shuffleState = ShuffleState.default,
                )
        }
    }

    data class ControllerState(
        val playState: PlayState,
        val nextState: NextState,
        val previousState: PreviousState,
        val itemState: ItemState,
    ) {

        data class PlayState(
            val isPlay: Boolean,
            val onPlayClick: () -> Unit,
        ) {

            companion object {
                val default: PlayState
                    get() = PlayState(
                        isPlay = false,
                        onPlayClick = {},
                    )
            }
        }

        data class NextState(
            val isNext: Boolean,
            val onNextClick: () -> Unit,
        ) {

            companion object {
                val default: NextState
                    get() = NextState(
                        isNext = false,
                        onNextClick = {},
                    )
            }
        }

        data class PreviousState(
            val isPrevious: Boolean,
            val onPreviousClick: () -> Unit,
        ) {

            companion object {
                val default: PreviousState
                    get() = PreviousState(
                        isPrevious = false,
                        onPreviousClick = {},
                    )
            }
        }

        data class ItemState(
            val item: MediaItem?,
        ) {

            val nameText: String
                get() = mediaMetadata?.title.toString()

            val artistText: String
                get() = mediaMetadata?.artist.toString()

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
                collapsableState = CollapsableState.default,
                controllerState = ControllerState.default,
                mediaState = MediaState.default,
            )
    }
}
