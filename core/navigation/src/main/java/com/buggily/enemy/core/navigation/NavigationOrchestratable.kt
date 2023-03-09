package com.buggily.enemy.core.navigation

import kotlinx.coroutines.flow.StateFlow

interface NavigationOrchestratable {
    val eventState: StateFlow<NavigationEventState>
    fun navigate(args: NavigationArgs)
}
