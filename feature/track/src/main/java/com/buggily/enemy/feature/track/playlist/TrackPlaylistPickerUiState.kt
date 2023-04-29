package com.buggily.enemy.feature.track.playlist

import com.buggily.enemy.data.playlist.Playlist

data class TrackPlaylistPickerUiState(
    val playlistState: PlaylistState,
) {

    data class PlaylistState(
        val onClick: (Playlist) -> Unit,
    ) {

        companion object {
            val default: PlaylistState
                get() = PlaylistState {}
        }
    }

    companion object {
        val default: TrackPlaylistPickerUiState
            get() = TrackPlaylistPickerUiState(
                playlistState = PlaylistState.default,
            )
    }
}
