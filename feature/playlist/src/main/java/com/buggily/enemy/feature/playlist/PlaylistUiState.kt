package com.buggily.enemy.feature.playlist

import com.buggily.enemy.domain.playlist.PlaylistUi
import com.buggily.enemy.domain.track.TrackWithIndexUi

data class PlaylistUiState(
    val playlistState: PlaylistState,
    val trackState: TrackState,
) {

    data class PlaylistState(
        val playlist: PlaylistUi?,
        val onStartClick: () -> Unit,
    )

    data class TrackState(
        val track: TrackWithIndexUi?,
        val onClick: (TrackWithIndexUi) -> Unit,
        val onLongClick: (TrackWithIndexUi) -> Unit,
    )
}
