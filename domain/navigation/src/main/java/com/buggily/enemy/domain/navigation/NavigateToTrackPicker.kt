package com.buggily.enemy.domain.navigation

import com.buggily.enemy.core.navigation.NavigationArgs
import com.buggily.enemy.core.navigation.NavigationDestination

class NavigateToTrackPicker(
    private val navigate: Navigate,
) {

    operator fun invoke(trackId: Long) {
        val route: String = NavigationDestination.Track.Picker.getRoute(
            trackId = trackId,
        )

        navigate(NavigationArgs.Route.WithoutOptions(route))
    }
}
