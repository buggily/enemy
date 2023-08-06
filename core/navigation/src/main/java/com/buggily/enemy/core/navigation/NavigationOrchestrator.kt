package com.buggily.enemy.core.navigation

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class NavigationOrchestrator : NavigationOrchestratable {

    private val _eventState: MutableSharedFlow<NavigationEventState> = MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.SUSPEND,
    )

    override val eventState: SharedFlow<NavigationEventState>
        get() = _eventState

    override fun navigate(args: NavigationArgs) {
        NavigationEventState(
            args = args,
        ).let { _eventState.tryEmit(it) }
    }
}
