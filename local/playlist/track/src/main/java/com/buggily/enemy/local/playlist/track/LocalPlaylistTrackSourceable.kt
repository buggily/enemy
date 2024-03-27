package com.buggily.enemy.local.playlist.track

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface LocalPlaylistTrackSourceable {

    fun getPagingByPlaylistId(playlistId: Long): Flow<PagingData<LocalPlaylistTrack>>
    suspend fun getByPlaylistId(playlistId: Long): List<LocalPlaylistTrack>
    suspend fun getMaxIndexByPlaylistId(playlistId: Long): Int?

    suspend fun getByPlaylistIdAndIndex(
        playlistId: Long,
        index: Int,
    ): LocalPlaylistTrack?

    suspend fun insert(track: LocalPlaylistTrack)
    suspend fun delete(track: LocalPlaylistTrack)
    suspend fun deleteByPlaylistId(playlistId: Long)
}
