package com.buggily.enemy.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.currentBackStackEntryAsState
import com.buggily.enemy.navigation.EnemyDestination

class EnemyAppState(
    val navController: NavHostController,
    private val windowSizeClass: WindowSizeClass,
) {

    val isControllerVisible: Boolean
        @Composable get() = !isDestinationController

    private val isDestinationController: Boolean
        @Composable get() = destination is EnemyDestination.Controller

    private val destination: EnemyDestination?
        @Composable get() = EnemyDestination.get(navigationDestination)

    private val navigationDestination: NavDestination?
        @Composable get() = navigationBackStackEntry?.destination

    private val navigationBackStackEntry: NavBackStackEntry?
        @Composable get() = navController.currentBackStackEntryAsState().value

    fun navigate(
        route: String,
        builder: NavOptionsBuilder.() -> Unit,
    ) = navController.navigate(
        route = route,
        builder = builder,
    )
}
