package com.buggily.enemy.data.track.source

import androidx.paging.PagingData
import com.buggily.enemy.local.track.paging.Track
import kotlinx.coroutines.flow.Flow

interface TrackSourceable {

    fun getPaging(search: String): Flow<PagingData<Track>>
    fun getPagingByAlbumId(albumId: Long): Flow<PagingData<Track>>
    fun getPagingByPlaylistId(playlistId: Long): Flow<PagingData<Track>>

    suspend fun getById(id: Long): Track
    suspend fun getByAlbumId(albumId: Long): List<Track>
    suspend fun getByPlaylistId(playlistId: Long): List<Track>
}
