package com.buggily.enemy.data.playlist.source

import androidx.paging.PagingData
import com.buggily.enemy.local.playlist.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistSourceable {

    fun getPaging(search: String): Flow<PagingData<Playlist>>

    suspend fun getById(id: Long): Playlist
    suspend fun insert(playlist: Playlist)
    suspend fun update(playlist: Playlist)
    suspend fun delete(playlist: Playlist)
}
