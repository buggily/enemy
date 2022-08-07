package com.buggily.enemy.data.repository.album

import androidx.paging.PagingData
import com.buggily.enemy.data.Album
import com.buggily.enemy.data.source.album.AlbumSourceable
import kotlinx.coroutines.flow.Flow

class AlbumRepository(
    private val source: AlbumSourceable,
) : AlbumRepositable {
    override fun getPaging(search: String): Flow<PagingData<Album>> = source.getPaging(search)
    override fun getByAlbumId(albumId: Long?): Album? = source.getByAlbumId(albumId)
}
