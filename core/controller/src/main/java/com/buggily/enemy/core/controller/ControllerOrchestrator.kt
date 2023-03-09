package com.buggily.enemy.core.controller

import androidx.media3.common.MediaItem
import com.buggily.enemy.core.ext.firstIndex
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ControllerOrchestrator : ControllerOrchestratable {

    private val _eventState: MutableStateFlow<ControllerEventState> =
        MutableStateFlow(ControllerEventState.Default)

    override val eventState: StateFlow<ControllerEventState>
        get() = _eventState

    override fun playItems(
        index: Int,
        items: List<MediaItem>,
    ) = _eventState.update {
        ControllerEventState.Event.Play.With(
            index = index,
            items = items,
            onEvent = ::resetEventState
        )
    }

    override fun playItem(item: MediaItem) {
        val items: List<MediaItem> = listOf(item)

        playItems(
            index = items.firstIndex,
            items = items,
        )
    }

    override fun play() = _eventState.update {
        ControllerEventState.Event.Play.Without(::resetEventState)
    }

    override fun pause() = _eventState.update {
        ControllerEventState.Event.Pause(::resetEventState)
    }

    override fun previous() = _eventState.update {
        ControllerEventState.Event.Previous(::resetEventState)
    }

    override fun next() = _eventState.update {
        ControllerEventState.Event.Next(::resetEventState)
    }

    override fun repeat(repeatMode: Int) = _eventState.update {
        ControllerEventState.Event.Repeat(
            repeatMode = repeatMode,
            onEvent = ::resetEventState,
        )
    }

    override fun shuffle(shuffleMode: Boolean) = _eventState.update {
        ControllerEventState.Event.Shuffle(
            shuffleMode = shuffleMode,
            onEvent = ::resetEventState,
        )
    }

    override fun seek(milliseconds: Long) = _eventState.update {
        ControllerEventState.Event.Seek(
            milliseconds = milliseconds,
            onEvent = ::resetEventState,
        )
    }

    private fun resetEventState() = _eventState.update {
        ControllerEventState.Default
    }
}
