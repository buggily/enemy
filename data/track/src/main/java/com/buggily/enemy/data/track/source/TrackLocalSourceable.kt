package com.buggily.enemy.data.track.source

import androidx.paging.PagingData
import com.buggily.enemy.local.track.Track
import kotlinx.coroutines.flow.Flow

interface TrackLocalSourceable {

    fun getPagingByPlaylistId(playlistId: Long): Flow<PagingData<Track>>
    suspend fun getByPlaylistId(playlistId: Long): List<Track>
}
