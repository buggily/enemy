package com.buggily.enemy.feature.playlist

import com.buggily.enemy.data.playlist.Playlist
import com.buggily.enemy.data.track.TrackWithIndex

data class PlaylistUiState(
    val playlist: Playlist?,
    val trackState: TrackState,
) {

    data class TrackState(
        val onClick: (TrackWithIndex) -> Unit,
        val onLongClick: (TrackWithIndex) -> Unit,
    ) {

        companion object {
            val default: TrackState
                get() = TrackState(
                    onClick = {},
                    onLongClick = {},
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
