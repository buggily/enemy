package com.buggily.enemy.data.album.source

import androidx.paging.PagingData
import com.buggily.enemy.local.album.Album
import kotlinx.coroutines.flow.Flow

interface AlbumSourceable {
    fun getPaging(search: String): Flow<PagingData<Album>>
    fun getByAlbumId(albumId: Long): Album?
}
