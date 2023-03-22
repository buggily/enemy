package com.buggily.enemy.feature.playlist.create

data class CreatePlaylistUiState(
    val nameState: NameState,
    val confirmState: ConfirmState,
) {

    data class NameState(
        val value: String,
        val onChange: (String) -> Unit,
    ) {

        companion object {
            val default: NameState
                get() = NameState(
                    value = String(),
                    onChange = {},
                )
        }
    }

    data class ConfirmState(
        val isEnabled: Boolean,
        val onClick: () -> Unit,
    ) {

        companion object {
            val default: ConfirmState
                get() = ConfirmState(
                    isEnabled = false,
                    onClick = {},
                )
        }
    }

    companion object {
        val default: CreatePlaylistUiState
            get() = CreatePlaylistUiState(
                nameState = NameState.default,
                confirmState = ConfirmState.default,
            )
    }
}
