package com.buggily.enemy.domain.navigation

import com.buggily.enemy.core.navigation.NavigationArgs
import com.buggily.enemy.core.navigation.NavigationDestination

class NavigateToEditPlaylist(
    private val navigate: Navigate,
) {

    operator fun invoke(
        playlistId: Long,
    ) {
        val route: String = NavigationDestination.Playlist.Edit.getRoute(
            playlistId = playlistId,
        )

        navigate(NavigationArgs.Route.WithoutOptions(route))
    }
}
