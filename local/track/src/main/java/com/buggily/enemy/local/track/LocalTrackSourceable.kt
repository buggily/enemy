package com.buggily.enemy.local.track

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface LocalTrackSourceable {

    fun getPagingByPlaylistId(playlistId: Long): Flow<PagingData<LocalTrack>>
    suspend fun getByPlaylistId(playlistId: Long): List<LocalTrack>
    suspend fun getMaxIndexByPlaylistId(playlistId: Long): Int?

    suspend fun getByPlaylistIdAndIndex(
        playlistId: Long,
        index: Int,
    ): LocalTrack?

    suspend fun insert(track: LocalTrack)
    suspend fun delete(track: LocalTrack)
    suspend fun deleteByPlaylistId(playlistId: Long)
}
