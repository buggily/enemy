package com.buggily.enemy.core.ui

import com.buggily.enemy.core.data.TimeOfDay

data class GlobalUiState(
    val greetingState: GreetingState,
    val searchState: SearchState,
    val createPlaylistState: CreatePlaylistState,
    val preferencesState: PreferencesState,
    val controllerState: ControllerState,
) {

    data class GreetingState(
        val timeOfDay: TimeOfDay?,
        val isVisible: Boolean,
        val onVisible: () -> Unit,
    ) {

        companion object {
            val default: GreetingState
                get() = GreetingState(
                    timeOfDay = null,
                    isVisible = true,
                    onVisible = {},
                )
        }
    }

    data class SearchState(
        val value: String,
        val isVisible: Boolean,
        val onClick: () -> Unit,
        val onChange: (String) -> Unit,
        val onClear: () -> Unit,
    ) {

        val isEnabled: Boolean
            get() = isVisible

        companion object {
            val default: SearchState
                get() = SearchState(
                    value = String(),
                    isVisible = false,
                    onClick = {},
                    onChange = {},
                    onClear = {},
                )
        }
    }

    data class CreatePlaylistState(
        val to: () -> Unit,
    ) {

        companion object {
            val default: CreatePlaylistState
                get() = CreatePlaylistState(
                    to = {},
                )
        }
    }

    data class PreferencesState(
        val to: () -> Unit,
    ) {

        companion object {
            val default: PreferencesState
                get() = PreferencesState(
                    to = {},
                )
        }
    }

    data class ControllerState(
        val to: () -> Unit,
    ) {

        companion object {
            val default: ControllerState
                get() = ControllerState(
                    to = {},
                )
        }
    }

    companion object {
        val default: GlobalUiState
            get() = GlobalUiState(
                greetingState = GlobalUiState.GreetingState.default,
                searchState = GlobalUiState.SearchState.default,
                createPlaylistState = GlobalUiState.CreatePlaylistState.default,
                preferencesState = GlobalUiState.PreferencesState.default,
                controllerState = GlobalUiState.ControllerState.default,
            )
    }
}
