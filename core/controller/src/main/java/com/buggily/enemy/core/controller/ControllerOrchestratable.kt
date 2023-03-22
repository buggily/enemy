package com.buggily.enemy.core.controller

import androidx.media3.common.MediaItem
import kotlinx.coroutines.flow.StateFlow

interface ControllerOrchestratable {

    val eventState: StateFlow<ControllerEventState>

    fun playItems(
        index: Int,
        items: List<MediaItem>,
    )

    fun playItem(
        item: MediaItem,
    )

    fun play()
    fun pause()

    fun previous()
    fun next()

    fun repeat(
        repeatMode: Int,
    )

    fun shuffle(
        shuffleMode: Boolean,
    )

    fun seek(
        milliseconds: Long,
    )
}
