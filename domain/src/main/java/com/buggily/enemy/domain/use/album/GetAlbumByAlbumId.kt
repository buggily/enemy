package com.buggily.enemy.domain.use.album

import com.buggily.enemy.data.repository.album.AlbumRepositable
import com.buggily.enemy.domain.album.Album
import com.buggily.enemy.domain.map.album.AlbumMapper

class GetAlbumByAlbumId(
    private val repository: AlbumRepositable,
    private val mapper: AlbumMapper
) {

    operator fun invoke(albumId: Long): Album? = repository.getByAlbumId(albumId)?.let {
        mapper.mapTo(it)
    }
}
