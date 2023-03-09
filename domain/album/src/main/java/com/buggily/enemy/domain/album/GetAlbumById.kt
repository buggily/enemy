package com.buggily.enemy.domain.album

import com.buggily.enemy.core.model.album.Album
import com.buggily.enemy.data.album.repository.AlbumRepositable

class GetAlbumById(
    private val repository: AlbumRepositable,
) {

    suspend operator fun invoke(
        id: Long,
    ): Album = repository.getById(
        id = id,
    )
}
