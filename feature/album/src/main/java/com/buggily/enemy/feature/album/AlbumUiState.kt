package com.buggily.enemy.feature.album

import com.buggily.enemy.data.album.Album
import com.buggily.enemy.data.track.Track

data class AlbumUiState(
    val album: Album?,
    val albumState: AlbumState,
    val trackState: TrackState,
) {

    data class AlbumState(
        val onStartClick: () -> Unit,
    )

    data class TrackState(
        val onClick: (Track) -> Unit,
        val onLongClick: (Track) -> Unit,
    )
}
