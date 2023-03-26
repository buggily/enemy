package com.buggily.enemy.data.album.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.buggily.enemy.core.model.album.Album
import com.buggily.enemy.data.album.ext.map
import com.buggily.enemy.data.album.source.AlbumExternalSourceable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.buggily.enemy.external.album.Album as ExternalAlbum

class AlbumRepository(
    private val source: AlbumExternalSourceable,
) : AlbumRepositable {

    override suspend fun getById(
        id: Long,
    ): Album = source.getById(
        id = id,
    ).map()

    override fun getPaging(
        search: String,
    ): Flow<PagingData<Album>> = source.getPaging(
        search = search,
    ).map { pagingData: PagingData<ExternalAlbum> ->
        pagingData.map { it.map() }
    }
}
