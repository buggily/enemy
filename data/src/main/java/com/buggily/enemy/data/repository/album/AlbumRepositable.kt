package com.buggily.enemy.data.repository.album

import androidx.paging.PagingData
import com.buggily.enemy.data.album.Album
import kotlinx.coroutines.flow.Flow

interface AlbumRepositable {
    fun getPaging(search: String): Flow<PagingData<Album>>
    fun getByAlbumId(albumId: Long): Album?
}