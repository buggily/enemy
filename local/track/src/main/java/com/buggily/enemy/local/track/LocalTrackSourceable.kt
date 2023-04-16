package com.buggily.enemy.local.track

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface LocalTrackSourceable {
    fun getPagingByPlaylistId(playlistId: Long): Flow<PagingData<LocalTrack>>
    suspend fun getByPlaylistId(playlistId: Long): List<LocalTrack>
}
