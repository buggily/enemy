package com.buggily.enemy.domain.navigation

import com.buggily.enemy.core.navigation.NavigationArgs
import com.buggily.enemy.core.navigation.NavigationDestination

class NavigateToPlaylistTrackPicker(
    private val navigate: Navigate,
) {

    operator fun invoke(
        playlistId: Long,
        trackIndex: Int,
    ) {
        val route: String = NavigationDestination.Playlist.TrackPicker.getRoute(
            playlistId = playlistId,
            trackIndex = trackIndex,
        )

        navigate(NavigationArgs.Route.WithoutOptions(route))
    }
}
