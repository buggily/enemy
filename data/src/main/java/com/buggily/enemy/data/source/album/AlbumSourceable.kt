package com.buggily.enemy.data.source.album

import androidx.paging.PagingData
import com.buggily.enemy.data.album.Album
import kotlinx.coroutines.flow.Flow

interface AlbumSourceable {
    fun getPaging(search: String): Flow<PagingData<Album>>
    fun getByAlbumId(albumId: Long): Album?
}
