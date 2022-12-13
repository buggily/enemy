package com.buggily.enemy.data.album.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.buggily.enemy.core.model.album.Album
import com.buggily.enemy.data.album.ext.map
import com.buggily.enemy.data.album.source.AlbumSourceable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AlbumRepository(
    private val source: AlbumSourceable,
) : AlbumRepositable {

    override fun getByAlbumId(
        albumId: Long,
    ): Album? = source.getByAlbumId(albumId)?.map()

    override fun getPaging(
        search: String,
    ): Flow<PagingData<Album>> = source.getPaging(search).map {
        it.map { album -> album.map() }
    }
}
