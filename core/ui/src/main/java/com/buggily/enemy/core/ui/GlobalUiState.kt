package com.buggily.enemy.core.ui

data class GlobalUiState(
    val searchState: SearchState,
    val createPlaylistState: CreatePlaylistState,
    val preferencesState: PreferencesState,
    val controllerState: ControllerState,
) {

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
                searchState = GlobalUiState.SearchState.default,
                createPlaylistState = GlobalUiState.CreatePlaylistState.default,
                preferencesState = GlobalUiState.PreferencesState.default,
                controllerState = GlobalUiState.ControllerState.default,
            )
    }
}
