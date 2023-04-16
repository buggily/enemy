package com.buggily.enemy.domain.album

import com.buggily.enemy.data.album.Album
import com.buggily.enemy.data.album.AlbumRepositable

class GetAlbumById(
    private val repository: AlbumRepositable,
) {

    suspend operator fun invoke(
        id: Long,
    ): Album = repository.getById(
        id = id,
    )
}
