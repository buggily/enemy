package com.buggily.enemy.domain.navigation

import com.buggily.enemy.core.navigation.NavigationArgs
import com.buggily.enemy.core.navigation.NavigationDestination

class NavigateToPreferences(
    private val navigate: Navigate,
) {

    operator fun invoke() {
        val route: String = NavigationDestination.Preferences.route

        NavigationArgs.Route.WithOptions(route) {
            launchSingleTop = true
            restoreState = false
        }.let { navigate(it) }
    }
}
