package com.buggily.enemy.ui

import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.buggily.enemy.domain.album.Album

class EnemyState(
    val navController: NavHostController,
    val activityViewModelStoreOwner: ViewModelStoreOwner,
) {

    val orientationState: OrientationState = OrientationState {
        val currentDestination: NavDestination = navController.currentDestination ?: return@OrientationState
        val currentRoute: String = currentDestination.route ?: return@OrientationState

        navigate(EnemyDestination.Home.route) {
            launchSingleTop = true
            restoreState = false

            popUpTo(currentRoute) {
                inclusive = true
                saveState = false
            }
        }
    }

    val settingsState: SettingsState = SettingsState {
        navigate(EnemyDestination.Settings.route) {
            launchSingleTop = true
            restoreState = false
        }
    }

    val albumState: AlbumState = AlbumState {
        navigate(EnemyDestination.Album.getRoute(it.id)) {
            launchSingleTop = true
            restoreState = false
        }
    }

    data class OrientationState(
        val onHomeClick: () -> Unit,
    )

    data class SettingsState(
        val onSettingsClick: () -> Unit,
    )

    data class AlbumState(
        val onAlbumClick: (Album) -> Unit,
    )

    private fun navigate(
        route: String,
        builder: NavOptionsBuilder.() -> Unit,
    ) {
        val currentDestination: NavDestination = navController.currentDestination ?: return
        val currentRoute: String = currentDestination.route ?: return
        if (route == currentRoute) return

        navController.navigate(
            route = route,
            builder = builder,
        )
    }
}
