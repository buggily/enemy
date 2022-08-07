package com.buggily.enemy.domain.use.album

import androidx.paging.PagingData
import androidx.paging.map
import com.buggily.enemy.data.repository.album.AlbumRepositable
import com.buggily.enemy.domain.album.Album
import com.buggily.enemy.domain.map.album.AlbumMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAlbumsPaging(
    private val repository: AlbumRepositable,
    private val mapper: AlbumMapper,
) {

    operator fun invoke(
        search: String,
    ): Flow<PagingData<Result<Album>>> = repository.getPaging(search).map { paging ->
        paging.map { mapper.mapToResult(it) }
    }
}
