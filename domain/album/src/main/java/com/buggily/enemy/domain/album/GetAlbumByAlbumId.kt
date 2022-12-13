package com.buggily.enemy.domain.album

import com.buggily.enemy.data.album.repository.AlbumRepositable

class GetAlbumByAlbumId(
    private val repository: AlbumRepositable,
) {

    operator fun invoke(albumId: Long) = repository.getByAlbumId(albumId)
}
