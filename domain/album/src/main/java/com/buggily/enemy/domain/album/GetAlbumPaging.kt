package com.buggily.enemy.domain.album

import androidx.paging.PagingData
import androidx.paging.map
import com.buggily.enemy.data.album.Album
import com.buggily.enemy.data.album.AlbumRepositable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAlbumPaging(
    private val albumRepository: AlbumRepositable,
) {

    operator fun invoke(
        search: String,
    ): Flow<PagingData<AlbumUi>> = albumRepository.getPaging(search).map {
        it.map { album: Album -> album.toUi() }
    }
}
