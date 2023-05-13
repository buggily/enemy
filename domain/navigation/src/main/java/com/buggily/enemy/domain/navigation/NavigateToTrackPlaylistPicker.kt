package com.buggily.enemy.domain.navigation

import com.buggily.enemy.core.navigation.NavigationArgs
import com.buggily.enemy.core.navigation.NavigationDestination

class NavigateToTrackPlaylistPicker(
    private val navigate: Navigate,
) {

    operator fun invoke(
        trackId: Long,
    ) {
        val route: String = NavigationDestination.Track.PlaylistPicker.getRoute(
            trackId = trackId,
        )

        NavigationArgs.Route.WithOptions(route) {
            popUpTo(NavigationDestination.Track.Picker.route) {
                inclusive = true
                saveState = false
            }
        }.let { navigate(it) }
    }
}
