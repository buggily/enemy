package com.buggily.enemy.core.controller

import androidx.media3.common.MediaItem
import com.buggily.enemy.core.ext.firstIndex
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class ControllerOrchestrator : ControllerOrchestratable {

    private val _event: MutableSharedFlow<ControllerEvent> = MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.SUSPEND,
    )

    override val event: SharedFlow<ControllerEvent>
        get() = _event

    override fun playItems(
        index: Int,
        items: List<MediaItem>,
    ) {
        ControllerEvent.Play.With(
            index = index,
            items = items,
        ).let { _event.tryEmit(it) }
    }

    override fun playItem(item: MediaItem) = listOf(item).let {
        playItems(
            index = it.firstIndex,
            items = it,
        )
    }

    override fun play() {
        _event.tryEmit(ControllerEvent.Play.Without)
    }

    override fun pause() {
        _event.tryEmit(ControllerEvent.Pause)
    }

    override fun previous() {
        _event.tryEmit(ControllerEvent.Previous)
    }

    override fun next() {
        _event.tryEmit(ControllerEvent.Next)
    }

    override fun repeat(repeatMode: Int) {
        ControllerEvent.Repeat(
            repeatMode = repeatMode,
        ).let { _event.tryEmit(it) }
    }

    override fun shuffle(shuffleMode: Boolean) {
        ControllerEvent.Shuffle(
            shuffleMode = shuffleMode,
        ).let { _event.tryEmit(it) }
    }

    override fun seek(milliseconds: Long) {
        ControllerEvent.Seek(
            milliseconds = milliseconds,
        ).let { _event.tryEmit(it) }
    }
}
