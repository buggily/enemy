package com.buggily.enemy.domain.navigation

import com.buggily.enemy.core.navigation.NavigationArgs
import com.buggily.enemy.core.navigation.NavigationDestination

class NavigateToCreatePlaylist(
    private val navigate: Navigate,
) {

    operator fun invoke(name: String) {
        val route: String = NavigationDestination.Playlist.Create.getRoute(
            name = name,
        )

        navigate(NavigationArgs.Route.WithoutOptions(route))
    }
}
