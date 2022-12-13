package com.buggily.enemy.domain.album

import com.buggily.enemy.data.album.repository.AlbumRepositable

class GetAlbumsPaging(
    private val repository: AlbumRepositable,
) {

    operator fun invoke(search: String) = repository.getPaging(search)
}
