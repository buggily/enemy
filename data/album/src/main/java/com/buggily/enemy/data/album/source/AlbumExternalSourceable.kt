package com.buggily.enemy.data.album.source

import androidx.paging.PagingData
import com.buggily.enemy.external.album.Album
import kotlinx.coroutines.flow.Flow

interface AlbumExternalSourceable {
    fun getPaging(search: String): Flow<PagingData<com.buggily.enemy.external.album.Album>>
    suspend fun getById(id: Long): com.buggily.enemy.external.album.Album
}
