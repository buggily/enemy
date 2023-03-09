package com.buggily.enemy.domain.navigation

import com.buggily.enemy.core.navigation.NavigationArgs
import com.buggily.enemy.core.navigation.NavigationDestination

class NavigateFromOrientationToBrowse(
    private val navigate: Navigate,
) {

    operator fun invoke() {
        val route: String = NavigationDestination.Browse.route

        NavigationArgs.Route.WithOptions(route) {
            launchSingleTop = true
            restoreState = false

            popUpTo(NavigationDestination.Orientation.route) {
                inclusive = true
                saveState = false
            }
        }.let { navigate(it) }
    }
}
