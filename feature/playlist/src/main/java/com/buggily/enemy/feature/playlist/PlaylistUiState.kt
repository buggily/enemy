package com.buggily.enemy.feature.playlist

import com.buggily.enemy.data.playlist.Playlist
import com.buggily.enemy.data.track.Track

data class PlaylistUiState(
    val playlist: Playlist?,
    val trackState: TrackState,
) {

    data class TrackState(
        val onClick: (Track) -> Unit,
    ) {

        companion object {
            val default: TrackState
                get() = TrackState(
                    onClick = {},
                )
        }
    }

    companion object {
        val default: PlaylistUiState
            get() = PlaylistUiState(
                playlist = null,
                trackState = TrackState.default,
            )
    }
}
