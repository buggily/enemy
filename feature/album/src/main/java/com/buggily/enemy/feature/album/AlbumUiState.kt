package com.buggily.enemy.feature.album

import com.buggily.enemy.domain.album.AlbumUi
import com.buggily.enemy.domain.track.TrackUi

data class AlbumUiState(
    val album: AlbumUi?,
    val albumState: AlbumState,
    val trackState: TrackState,
) {

    data class AlbumState(
        val onStartClick: () -> Unit,
    )

    data class TrackState(
        val onClick: (TrackUi) -> Unit,
        val onLongClick: (TrackUi) -> Unit,
    )
}
