package com.buggily.enemy.feature.playlist.create

data class CreatePlaylistUiState(
    val nameState: NameState,
    val confirmState: ConfirmState,
) {

    data class NameState(
        val value: String,
        val onChange: (String) -> Unit,
    )

    data class ConfirmState(
        val isEnabled: Boolean,
        val onClick: () -> Unit,
    )
}
