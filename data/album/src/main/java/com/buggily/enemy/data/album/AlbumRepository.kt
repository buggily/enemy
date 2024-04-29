package com.buggily.enemy.data.album

import androidx.paging.PagingData
import androidx.paging.map
import com.buggily.enemy.external.album.ExternalAlbum
import com.buggily.enemy.external.album.ExternalAlbumSourceable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class AlbumRepository(
    private val albumExternalSource: ExternalAlbumSourceable,
) : AlbumRepositable {

    override suspend fun getById(
        id: Long,
    ): Album? = albumExternalSource.getById(
        id = id,
    )?.to()

    override fun getPaging(
        search: String,
    ): Flow<PagingData<Album>> = albumExternalSource.getPaging(
        search = search,
    ).map { pagingData: PagingData<ExternalAlbum> ->
        pagingData.map { it.to() }
    }
}
