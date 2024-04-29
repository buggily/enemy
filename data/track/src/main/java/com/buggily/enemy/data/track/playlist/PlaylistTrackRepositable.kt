package com.buggily.enemy.data.track.playlist

import androidx.paging.PagingData
import com.buggily.enemy.data.track.TrackWithIndex
import kotlinx.coroutines.flow.Flow

interface PlaylistTrackRepositable {

    fun getPagingByPlaylistId(
        playlistId: Long,
    ): Flow<PagingData<TrackWithIndex>>

    suspend fun getByPlaylistId(
        playlistId: Long,
    ): List<TrackWithIndex>

    suspend fun getByPlaylistIdAndIndex(
        playlistId: Long,
        index: Int,
    ): TrackWithIndex?

    suspend fun insertByIdAndPlaylistId(
        id: Long,
        playlistId: Long,
    )

    suspend fun deleteByIdAndPlaylistIdAndIndex(
        id: Long,
        playlistId: Long,
        index: Int,
    )

    suspend fun deleteByPlaylistId(
        playlistId: Long,
    )
}