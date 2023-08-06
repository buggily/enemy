package com.buggily.enemy.core.controller

import androidx.media3.common.MediaItem

sealed class ControllerEventState {

    sealed class Play : ControllerEventState() {

        data class With(
            val index: Int,
            val items: List<MediaItem>,
        ) : Play()

        object Without : Play()
    }

    object Pause : ControllerEventState()

    object Next : ControllerEventState()
    object Previous : ControllerEventState()

    data class Repeat(
        val repeatMode: Int,
    ) : ControllerEventState()

    data class Shuffle(
        val shuffleMode: Boolean,
    ) : ControllerEventState()

    data class Seek(
        val milliseconds: Long,
    ) : ControllerEventState()
}
