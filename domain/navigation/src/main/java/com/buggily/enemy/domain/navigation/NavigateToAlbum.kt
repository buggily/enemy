package com.buggily.enemy.domain.navigation

import com.buggily.enemy.core.navigation.NavigationArgs
import com.buggily.enemy.core.navigation.NavigationDestination

class NavigateToAlbum(
    private val navigate: Navigate,
) {

    operator fun invoke(albumId: Long) {
        val route: String = NavigationDestination.Album.getRoute(
            albumId = albumId,
        )

        NavigationArgs.Route.WithOptions(route) {
            launchSingleTop = true
            restoreState = false
        }.let { navigate(it) }
    }
}
