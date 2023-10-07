package com.buggily.enemy.core.navigation

import androidx.navigation.NavOptionsBuilder

sealed interface NavigationArgs {

    sealed interface Back : NavigationArgs {

        data object WithoutOptions : Back

        data class WithOptions(
            val route: String,
        ) : Back
    }

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
