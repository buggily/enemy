package com.buggily.enemy.feature.playlist

import com.buggily.enemy.data.playlist.Playlist
import com.buggily.enemy.data.track.TrackWithIndex

data class PlaylistUiState(
    val playlist: Playlist?,
    val playlistState: PlaylistState,
    val trackState: TrackState,
) {

    data class PlaylistState(
        val onStartClick: () -> Unit,
    )

    data class TrackState(
        val onClick: (TrackWithIndex) -> Unit,
        val onLongClick: (TrackWithIndex) -> Unit,
    )
}
