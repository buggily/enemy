package com.buggily.enemy.domain.album

import com.buggily.enemy.data.album.AlbumRepositable

class GetAlbumById(
    private val albumRepository: AlbumRepositable,
) {

    suspend operator fun invoke(
        id: Long,
    ): AlbumUi? = albumRepository.getById(
        id = id,
    )?.toUi()
}
