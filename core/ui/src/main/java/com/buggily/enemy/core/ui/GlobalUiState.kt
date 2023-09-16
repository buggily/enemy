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
}
