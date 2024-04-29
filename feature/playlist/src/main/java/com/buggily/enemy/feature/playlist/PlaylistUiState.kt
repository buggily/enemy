package com.buggily.enemy.feature.playlist

import com.buggily.enemy.domain.playlist.PlaylistUi
import com.buggily.enemy.domain.track.TrackWithIndexUi

data class PlaylistUiState(
    val playlist: PlaylistUi?,
    val playlistState: PlaylistState,
    val trackState: TrackState,
) {

    data class PlaylistState(
        val onStartClick: () -> Unit,
    )

    data class TrackState(
        val onClick: (TrackWithIndexUi) -> Unit,
        val onLongClick: (TrackWithIndexUi) -> Unit,
    )
}
