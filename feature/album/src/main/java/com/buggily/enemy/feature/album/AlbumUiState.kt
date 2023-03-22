package com.buggily.enemy.feature.album

import com.buggily.enemy.core.model.album.Album
import com.buggily.enemy.core.model.track.Track

data class AlbumUiState(
    val album: Album?,
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
        val default: AlbumUiState
            get() = AlbumUiState(
                album = null,
                trackState = TrackState.default,
            )
    }
}
