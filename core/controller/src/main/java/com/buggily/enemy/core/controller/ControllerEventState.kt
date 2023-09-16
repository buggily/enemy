package com.buggily.enemy.core.controller

import androidx.media3.common.MediaItem

sealed interface ControllerEventState {

    sealed interface Play : ControllerEventState {

        data class With(
            val index: Int,
            val items: List<MediaItem>,
        ) : Play

        data object Without : Play
    }

    data object Pause : ControllerEventState

    data object Next : ControllerEventState
    data object Previous : ControllerEventState

    data class Repeat(
        val repeatMode: Int,
    ) : ControllerEventState

    data class Shuffle(
        val shuffleMode: Boolean,
    ) : ControllerEventState

    data class Seek(
        val milliseconds: Long,
    ) : ControllerEventState
}
