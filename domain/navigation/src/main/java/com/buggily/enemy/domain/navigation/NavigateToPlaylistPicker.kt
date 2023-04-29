package com.buggily.enemy.domain.navigation

import com.buggily.enemy.core.navigation.NavigationArgs
import com.buggily.enemy.core.navigation.NavigationDestination

class NavigateToPlaylistPicker(
    private val navigate: Navigate,
) {

    operator fun invoke(playlistId: Long) {
        val route: String = NavigationDestination.Playlist.Picker.getRoute(
            playlistId = playlistId,
        )

        navigate(NavigationArgs.Route.WithoutOptions(route))
    }
}
