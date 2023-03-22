package com.buggily.enemy.domain.navigation

import com.buggily.enemy.core.navigation.NavigationArgs
import com.buggily.enemy.core.navigation.NavigationDestination

class NavigateFromPlaylistsToPlaylist(
    private val navigate: Navigate,
) {

    operator fun invoke(id: Long) {
        val route: String = NavigationDestination.Playlist.getRoute(
            id = id,
        )

        NavigationArgs.Route.WithOptions(route) {
            launchSingleTop = true
            restoreState = false
        }.let { navigate(it) }
    }
}
