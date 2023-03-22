package com.buggily.enemy.domain.navigation

import com.buggily.enemy.core.navigation.NavigationArgs
import com.buggily.enemy.core.navigation.NavigationDestination

class NavigateToController(
    private val navigate: Navigate,
) {

    operator fun invoke() {
        val route: String = NavigationDestination.Controller.route

        NavigationArgs.Route.WithOptions(route) {
            launchSingleTop = true
            restoreState = true
        }.let { navigate(it) }
    }
}
