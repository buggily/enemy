package com.buggily.enemy.domain.navigation

import com.buggily.enemy.core.navigation.NavigationArgs
import com.buggily.enemy.core.navigation.NavigationDestination

class NavigateToPlaylist(
    private val navigate: Navigate,
) {

    operator fun invoke(playlistId: Long) {
        val route: String = NavigationDestination.Playlist.getRoute(
            playlistId = playlistId,
        )

        NavigationArgs.Route.WithOptions(route) {
            launchSingleTop = true
            restoreState = false
        }.let { navigate(it) }
    }
}
