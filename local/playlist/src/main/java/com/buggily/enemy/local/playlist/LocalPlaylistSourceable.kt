package com.buggily.enemy.local.playlist

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface LocalPlaylistSourceable {

    fun getPaging(search: String): Flow<PagingData<LocalPlaylist>>

    suspend fun getById(id: Long): LocalPlaylist?
    suspend fun deleteById(id: Long)

    suspend fun insert(playlist: LocalPlaylist)
}
