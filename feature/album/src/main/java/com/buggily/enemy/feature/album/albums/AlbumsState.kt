package com.buggily.enemy.feature.album.albums

import com.buggily.enemy.core.model.album.Album

object AlbumsState {

    data class AlbumState(
        val onAlbumClick: (Album) -> Unit,
    )
}
