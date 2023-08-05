package com.buggily.enemy.core.navigation

import kotlinx.coroutines.flow.SharedFlow

interface NavigationOrchestratable {
    val eventState: SharedFlow<NavigationEventState>
    fun navigate(args: NavigationArgs)
}
