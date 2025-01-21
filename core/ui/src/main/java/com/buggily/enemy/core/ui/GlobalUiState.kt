package com.buggily.enemy.core.ui

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.buggily.enemy.core.navigation.NavigationDestination

data class GlobalUiState(
    val searchState: SearchState,
    val createPlaylistState: CreatePlaylistState,
    val preferencesState: PreferencesState,
    val controllerState: ControllerState,
    val destinationState: DestinationState,
) {

    data class SearchState(
        val value: String,
        val isVisible: Boolean,
        val isSearchable: Boolean,
        val onClick: () -> Unit,
        val onChange: (String) -> Unit,
        val onClear: () -> Unit,
    ) {

        val isSearchTextVisible: Boolean
            get() = isVisible && isSearchable

        val isSearchButtonVisible: Boolean
            get() = !isVisible && isSearchable

        val isPreferencesButtonVisible: Boolean
            get() = !isVisible || !isSearchable
    }

    data class CreatePlaylistState(
        val to: () -> Unit,
    )

    data class PreferencesState(
        val to: () -> Unit,
    )

    data class ControllerState(
        val to: () -> Unit,
    )

    data class DestinationState(
        val destination: NavDestination?,
    ) {

        val isBottomBarVisible: Boolean
            get() = setOf(
                NavigationDestination.Browse,
            ).any {
                isDestinationInHierarchy(it)
            }

        val isControllerVisible: Boolean
            get() = setOf(
                NavigationDestination.Orientation,
                NavigationDestination.Controller,
            ).none {
                isDestinationInHierarchy(it)
            }

        val isPreferencesButtonVisible: Boolean
            get() = setOf(
                NavigationDestination.Preferences,
            ).none {
                isDestinationInHierarchy(it)
            }

        private fun isDestinationInHierarchy(
            destination: NavigationDestination,
        ): Boolean = navigationHierarchy.any {
            NavigationDestination.get(it) == destination
        }

        private val navigationHierarchy: Sequence<NavDestination>
            get() = destination?.hierarchy.orEmpty()
    }
}
