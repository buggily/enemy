package com.buggily.enemy.domain.navigation

import com.buggily.enemy.core.navigation.NavigationArgs
import com.buggily.enemy.core.navigation.NavigationDestination

class NavigateBackFromCreatePlaylist(
    private val navigate: Navigate,
) {

    operator fun invoke(name: String) {
        val route: String = NavigationDestination.Playlist.Create.getRoute(
            name = name,
        )

        NavigationArgs.Back.WithOptions(
            route = route,
        ).let { navigate(it) }
    }
}
