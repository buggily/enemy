package com.buggily.enemy.core.controller

import androidx.media3.common.MediaItem
import com.buggily.enemy.core.ext.firstIndex
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class ControllerOrchestrator : ControllerOrchestratable {

    private val _eventState: MutableSharedFlow<ControllerEventState> = MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.SUSPEND,
    )

    override val eventState: SharedFlow<ControllerEventState> get() = _eventState

    override fun playItems(
        index: Int,
        items: List<MediaItem>,
    ) {
        ControllerEventState.Play.With(
            index = index,
            items = items,
        ).let { _eventState.tryEmit(it) }
    }

    override fun playItem(item: MediaItem) = listOf(item).let {
        playItems(
            index = it.firstIndex,
            items = it,
        )
    }

    override fun play() {
        _eventState.tryEmit(ControllerEventState.Play.Without)
    }

    override fun pause() {
        _eventState.tryEmit(ControllerEventState.Pause)
    }

    override fun previous() {
        _eventState.tryEmit(ControllerEventState.Previous)
    }

    override fun next() {
        _eventState.tryEmit(ControllerEventState.Next)
    }

    override fun repeat(repeatMode: Int) {
        ControllerEventState.Repeat(
            repeatMode = repeatMode,
        ).let { _eventState.tryEmit(it) }
    }

    override fun shuffle(shuffleMode: Boolean) {
        ControllerEventState.Shuffle(
            shuffleMode = shuffleMode,
        ).let { _eventState.tryEmit(it) }
    }

    override fun seek(milliseconds: Long) {
        ControllerEventState.Seek(
            milliseconds = milliseconds,
        ).let { _eventState.tryEmit(it) }
    }
}
