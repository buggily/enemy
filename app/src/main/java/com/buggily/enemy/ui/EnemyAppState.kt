package com.buggily.enemy.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.currentBackStackEntryAsState
import com.buggily.enemy.navigation.EnemyDestination

class EnemyAppState(
    val navController: NavHostController,
    private val windowSizeClass: WindowSizeClass,
) {

    val isBottomBarVisible: Boolean
        @Composable get() = setOf(EnemyDestination.Browse).any {
            isDestinationInHierarchy(it)
        }

    val isControllerVisible: Boolean
        @Composable get() = setOf(
            EnemyDestination.Orientation,
            EnemyDestination.Controller,
        ).none {
            isDestinationInHierarchy(it)
        }

    val isPreferencesButtonVisible: Boolean
        @Composable get() = setOf(EnemyDestination.Preferences).none {
            isDestinationInHierarchy(it)
        }

    private val navigationHierarchy: Sequence<NavDestination>
        @Composable get() = navigationDestination?.hierarchy ?: emptySequence()

    private val navigationDestination: NavDestination?
        @Composable get() = navigationBackStackEntry?.destination

    private val navigationBackStackEntry: NavBackStackEntry?
        @Composable get() = navController.currentBackStackEntryAsState().value

    @Composable
    private fun isDestinationInHierarchy(
        destination: EnemyDestination,
    ): Boolean = navigationHierarchy.any {
        EnemyDestination.get(it) == destination
    }

    fun navigate(
        route: String,
        builder: NavOptionsBuilder.() -> Unit,
    ) = navController.navigate(
        route = route,
        builder = builder,
    )
}
