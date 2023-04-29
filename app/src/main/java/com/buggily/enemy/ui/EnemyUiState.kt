package com.buggily.enemy.ui

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.buggily.enemy.core.navigation.NavigationDestination

data class EnemyUiState(
    val destinationState: DestinationState,
    val orientationState: OrientationState,
) {

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
            get() = setOf(NavigationDestination.Preferences).none {
                isDestinationInHierarchy(it)
            }

        private fun isDestinationInHierarchy(
            destination: NavigationDestination,
        ): Boolean = navigationHierarchy.any {
            NavigationDestination.get(it) == destination
        }

        private val navigationHierarchy: Sequence<NavDestination>
            get() = destination?.hierarchy ?: emptySequence()

        companion object {
            val default: DestinationState
                get() = DestinationState(
                    destination = null,
                )
        }
    }

    data class OrientationState(
        val to: () -> Unit,
    ) {

        companion object {
            val default: OrientationState
                get() = OrientationState(
                    to = {},
                )
        }
    }

    companion object {
        val default: EnemyUiState
            get() = EnemyUiState(
                destinationState = DestinationState.default,
                orientationState = OrientationState.default,
            )
    }
}
