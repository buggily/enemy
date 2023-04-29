package com.buggily.enemy.feature.album

import com.buggily.enemy.data.album.Album
import com.buggily.enemy.data.track.Track

data class AlbumUiState(
    val album: Album?,
    val trackState: TrackState,
) {

    data class TrackState(
        val onClick: (Track) -> Unit,
        val onLongClick: (Track) -> Unit,
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
        val default: AlbumUiState
            get() = AlbumUiState(
                album = null,
                trackState = TrackState.default,
            )
    }
}
