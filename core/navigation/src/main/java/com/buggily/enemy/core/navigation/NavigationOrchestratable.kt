package com.buggily.enemy.core.navigation

import kotlinx.coroutines.flow.SharedFlow

interface NavigationOrchestratable {
    val event: SharedFlow<NavigationEvent>
    fun navigate(args: NavigationArgs)
}
