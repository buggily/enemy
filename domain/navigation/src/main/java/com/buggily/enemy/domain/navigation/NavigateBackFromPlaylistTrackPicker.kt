package com.buggily.enemy.domain.navigation

import com.buggily.enemy.core.navigation.NavigationArgs
import com.buggily.enemy.core.navigation.NavigationDestination

class NavigateBackFromPlaylistTrackPicker(
    private val navigate: Navigate,
) {

    operator fun invoke(
        trackId: Long,
        playlistId: Long,
        trackIndex: Int,
    ) {
        val route: String = NavigationDestination.Playlist.TrackPicker.getRoute(
            trackId = trackId,
            playlistId = playlistId,
            trackIndex = trackIndex,
        )

        NavigationArgs.Back.WithOptions(
            route = route,
        ).let { navigate(it) }
    }
}
