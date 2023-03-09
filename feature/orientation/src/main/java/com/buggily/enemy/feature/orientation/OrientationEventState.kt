package com.buggily.enemy.feature.orientation

sealed class OrientationEventState {

    object Default : OrientationEventState()

    data class Event(
        val onEvent: () -> Unit,
    ) : OrientationEventState()
}
