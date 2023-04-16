package com.buggily.enemy.data.track

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface TrackRepositable {

    fun getPaging(search: String): Flow<PagingData<Track>>
    fun getPagingByAlbumId(albumId: Long): Flow<PagingData<Track>>
    fun getPagingByPlaylistId(playlistId: Long): Flow<PagingData<Track>>

    suspend fun getById(id: Long): Track
    suspend fun getByAlbumId(albumId: Long): List<Track>
    suspend fun getByPlaylistId(playlistId: Long): List<Track>
}
