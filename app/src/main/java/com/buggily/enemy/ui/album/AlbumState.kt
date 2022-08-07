package com.buggily.enemy.ui.album

import com.buggily.enemy.domain.album.Album
import com.buggily.enemy.domain.track.Track

data class AlbumState(
    val albumState: State,
    val trackState: TrackState,
    val playState: PlayState,
) {

    data class State(
        val album: Album?,
    ) {

        companion object {
            val default: State
                get() = State(
                    album = null,
                )
        }
    }

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

    data class PlayState(
        val onPlayClick: (Album) -> Unit,
    ) {

        companion object {
            val default: PlayState
                get() = PlayState(
                    onPlayClick = {},
                )
        }
    }

    companion object {
        val default: AlbumState
            get() = AlbumState(
                albumState = State.default,
                trackState = TrackState.default,
                playState = PlayState.default,
            )
    }
}
