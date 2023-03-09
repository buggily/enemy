package com.buggily.enemy.domain.navigation

import com.buggily.enemy.core.navigation.NavigationArgs
import com.buggily.enemy.core.navigation.NavigationDestination

class NavigateToOrientation(
    private val navigate: Navigate,
) {

    operator fun invoke() {
        val route: String = NavigationDestination.Orientation.route

        NavigationArgs.Route.WithOptions(route) {
            launchSingleTop = true
            restoreState = false

            popUpTo(NavigationDestination.startDestination.route) {
                saveState = false
                inclusive = true
            }
        }.let { navigate(it) }
    }
}
