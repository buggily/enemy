package com.buggily.enemy.domain.navigation

import com.buggily.enemy.core.navigation.NavigationArgs
import com.buggily.enemy.core.navigation.NavigationDestination

class NavigateBackFromController(
    private val navigate: Navigate,
) {

    operator fun invoke() {
        val route: String = NavigationDestination.Controller.route

        NavigationArgs.Back.WithOptions(
            route = route,
        ).let { navigate(it) }
    }
}
