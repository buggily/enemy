package com.buggily.enemy.local.playlist

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface LocalPlaylistSourceable {

    fun getPaging(search: String): Flow<PagingData<LocalPlaylist>>

    suspend fun getById(id: Long): LocalPlaylist
    suspend fun insert(playlist: LocalPlaylist)
    suspend fun update(playlist: LocalPlaylist)
    suspend fun delete(playlist: LocalPlaylist)
}
