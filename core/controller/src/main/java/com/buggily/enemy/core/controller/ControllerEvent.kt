package com.buggily.enemy.core.controller

import androidx.media3.common.MediaItem

sealed interface ControllerEvent {

    sealed interface Play : ControllerEvent {

        data class WithMany(
            val index: Int,
            val items: List<MediaItem>,
        ) : Play

        data class WithOne(
            val item: MediaItem,
        ) : Play

        data object Without : Play
    }

    data object Pause : ControllerEvent

    data object Next : ControllerEvent
    data object Previous : ControllerEvent

    data class Repeat(
        val repeatMode: Int,
    ) : ControllerEvent

    data class Shuffle(
        val shuffleMode: Boolean,
    ) : ControllerEvent

    data class Seek(
        val milliseconds: Long,
    ) : ControllerEvent
}
