package com.buggily.enemy.ui.album

import com.buggily.enemy.domain.album.Album

data class AlbumState(
    val albumState: State,
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

    companion object {
        val default: AlbumState
            get() = AlbumState(
                albumState = State.default,
            )
    }
}
