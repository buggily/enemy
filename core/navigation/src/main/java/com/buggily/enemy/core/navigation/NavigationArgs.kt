package com.buggily.enemy.core.navigation

import androidx.navigation.NavOptionsBuilder

sealed interface NavigationArgs {

    data object Back : NavigationArgs

    sealed interface Route : NavigationArgs {

        val route: String

        data class WithoutOptions(
            override val route: String,
        ) : Route

        data class WithOptions(
            override val route: String,
            val builder: NavOptionsBuilder.() -> Unit,
        ) : Route
    }
}
