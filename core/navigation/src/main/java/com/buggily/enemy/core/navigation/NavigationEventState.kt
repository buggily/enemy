package com.buggily.enemy.core.navigation

sealed class NavigationEventState {

    object Default: NavigationEventState()

    data class Event(
        val args: NavigationArgs,
        val onEvent: () -> Unit,
    ) : NavigationEventState()
}
