package com.buggily.enemy.albums

import com.buggily.enemy.core.model.album.Album

object AlbumsState {

    data class AlbumState(
        val onClick: (Album) -> Unit,
    )

    data class PreferencesState(
        val onClick: () -> Unit,
    )
}
