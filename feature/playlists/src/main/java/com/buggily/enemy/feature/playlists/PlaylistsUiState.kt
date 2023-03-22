package com.buggily.enemy.feature.playlists

import com.buggily.enemy.core.model.playlist.Playlist

data class PlaylistsUiState(
    val playlistState: PlaylistState,
    val searchState: SearchState,
) {

    data class PlaylistState(
        val onClick: (Playlist) -> Unit,
    ) {

        companion object {
            val default: PlaylistState
                get() = PlaylistState(
                    onClick = {},
                )
        }
    }

    data class SearchState(
        val value: String,
    ) {

        companion object {
            val default: SearchState
                get() = SearchState(
                    value = String(),
                )
        }
    }

    companion object {
        val default: PlaylistsUiState
            get() = PlaylistsUiState(
                playlistState = PlaylistState.default,
                searchState = SearchState.default,
            )
    }
}
