package com.buggily.enemy.core.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class NavigationOrchestrator : NavigationOrchestratable {

    private val _eventState: MutableStateFlow<NavigationEventState> =
        MutableStateFlow(NavigationEventState.Default)

    override val eventState: StateFlow<NavigationEventState>
        get() = _eventState

    override fun navigate(args: NavigationArgs) = _eventState.update {
        NavigationEventState.Event(
            args = args,
            onEvent = ::resetEventState,
        )
    }

    private fun resetEventState() = _eventState.update {
        NavigationEventState.Default
    }
}
