package com.buggily.enemy.feature.playlists

import com.buggily.enemy.data.playlist.Playlist

data class PlaylistsUiState(
    val playlistState: PlaylistState,
    val searchState: SearchState,
) {

    data class PlaylistState(
        val onClick: (Playlist) -> Unit,
        val onLongClick: (Playlist) -> Unit,
    )

    data class SearchState(
        val value: String,
    )
}
