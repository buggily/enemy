package com.buggily.enemy.feature.album

import com.buggily.enemy.core.model.album.Album
import com.buggily.enemy.core.model.track.Track

data class AlbumState(
    val album: Album?,
) {

    data class TrackState(
        val onTrackClick: (Track) -> Unit,
    ) {

        companion object {
            val default: TrackState
                get() = TrackState(
                    onTrackClick = {},
                )
        }
    }

    companion object {
        val default: AlbumState
            get() = AlbumState(
                album = null,
            )
    }
}
