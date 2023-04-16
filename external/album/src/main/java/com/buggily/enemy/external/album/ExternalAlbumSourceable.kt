package com.buggily.enemy.external.album

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface ExternalAlbumSourceable {
    fun getPaging(search: String): Flow<PagingData<ExternalAlbum>>
    suspend fun getById(id: Long): ExternalAlbum
}
