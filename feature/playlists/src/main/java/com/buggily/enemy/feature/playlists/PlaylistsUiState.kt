package com.buggily.enemy.feature.playlists

import com.buggily.enemy.domain.playlist.PlaylistUi

data class PlaylistsUiState(
    val playlistState: PlaylistState,
    val searchState: SearchState,
) {

    data class PlaylistState(
        val onClick: (PlaylistUi) -> Unit,
        val onLongClick: (PlaylistUi) -> Unit,
    )

    data class SearchState(
        val value: String,
    )
}
