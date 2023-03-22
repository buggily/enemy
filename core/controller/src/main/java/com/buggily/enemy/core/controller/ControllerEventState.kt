package com.buggily.enemy.core.controller

import androidx.media3.common.MediaItem

sealed class ControllerEventState {

    object Default : ControllerEventState()

    sealed class Event(
        open val onEvent: () -> Unit,
    ) : ControllerEventState() {

        sealed class Play(
            override val onEvent: () -> Unit,
        ) : Event(
            onEvent = onEvent,
        ) {

            data class With(
                val index: Int,
                val items: List<MediaItem>,
                override val onEvent: () -> Unit,
            ) : Play(
                onEvent = onEvent,
            )

            data class Without(
                override val onEvent: () -> Unit,
            ) : Play(
                onEvent = onEvent,
            )
        }

        data class Pause(
            override val onEvent: () -> Unit,
        ) : Event(
            onEvent = onEvent,
        )

        data class Previous(
            override val onEvent: () -> Unit,
        ) : Event(
            onEvent = onEvent,
        )

        data class Next(
            override val onEvent: () -> Unit,
        ) : Event(
            onEvent = onEvent,
        )

        data class Repeat(
            val repeatMode: Int,
            override val onEvent: () -> Unit,
        ) : Event(
            onEvent = onEvent
        )

        data class Shuffle(
            val shuffleMode: Boolean,
            override val onEvent: () -> Unit,
        ) : Event(
            onEvent = onEvent,
        )

        data class Seek(
            val milliseconds: Long,
            override val onEvent: () -> Unit,
        ) : Event(
            onEvent = onEvent,
        )
    }
}
