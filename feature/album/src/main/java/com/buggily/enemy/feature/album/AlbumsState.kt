package com.buggily.enemy.feature.album

import com.buggily.enemy.core.model.album.Album

object AlbumsState {

    data class AlbumState(
        val onAlbumClick: (Album) -> Unit,
    )
}
