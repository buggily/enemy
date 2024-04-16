package com.buggily.enemy.domain.navigation

import com.buggily.enemy.core.navigation.NavigationArgs
import com.buggily.enemy.core.navigation.NavigationDestination

class NavigateBackFromEditPlaylist(
    private val navigate: Navigate,
) {

    operator fun invoke(
        playlistId: Long,
    ) {
        val route: String = NavigationDestination.Playlist.Picker.getRoute(
            playlistId = playlistId,
        )

        NavigationArgs.Back.WithOptions(
            route = route,
        ).let { navigate(it) }
    }
}