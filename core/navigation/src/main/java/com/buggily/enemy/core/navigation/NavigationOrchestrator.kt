package com.buggily.enemy.core.navigation

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class NavigationOrchestrator : NavigationOrchestratable {

    private val _event: MutableSharedFlow<NavigationEvent> = MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.SUSPEND,
    )

    override val event: SharedFlow<NavigationEvent>
        get() = _event

    override fun navigate(args: NavigationArgs) {
        NavigationEvent(
            args = args,
        ).let { _event.tryEmit(it) }
    }
}
