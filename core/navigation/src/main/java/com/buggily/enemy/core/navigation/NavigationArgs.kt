package com.buggily.enemy.core.navigation

import androidx.navigation.NavOptionsBuilder

sealed class NavigationArgs {

    object Back : NavigationArgs()

    sealed class Route(
        open val route: String,
    ) : NavigationArgs() {

        data class WithoutOptions(
            override val route: String,
        ) : Route(
            route = route,
        )

        data class WithOptions(
            override val route: String,
            val builder: NavOptionsBuilder.() -> Unit,
        ) : Route(
            route = route,
        )
    }
}
