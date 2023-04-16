package com.buggily.enemy.domain.album

import androidx.paging.PagingData
import com.buggily.enemy.data.album.Album
import com.buggily.enemy.data.album.AlbumRepositable
import kotlinx.coroutines.flow.Flow

class GetAlbumPaging(
    private val repository: AlbumRepositable,
) {

    operator fun invoke(
        search: String,
    ): Flow<PagingData<Album>> = repository.getPaging(
        search = search,
    )
}
