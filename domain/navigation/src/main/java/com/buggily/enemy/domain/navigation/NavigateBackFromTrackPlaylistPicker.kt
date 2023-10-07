package com.buggily.enemy.domain.navigation

import com.buggily.enemy.core.navigation.NavigationArgs
import com.buggily.enemy.core.navigation.NavigationDestination

class NavigateBackFromTrackPlaylistPicker(
    private val navigate: Navigate,
) {

    operator fun invoke(trackId: Long) {
        val route: String = NavigationDestination.Track.PlaylistPicker.getRoute(
            trackId = trackId,
        )

        NavigationArgs.Back.WithOptions(
            route = route,
        ).let { navigate(it) }
    }
}
