package com.buggily.enemy.data.track.playlist

import androidx.paging.PagingData
import com.buggily.enemy.data.track.Track
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

    suspend fun insertByPlaylistId(
        playlistId: Long,
        track: Track,
    )

    suspend fun deleteByPlaylistId(
        playlistId: Long,
        track: TrackWithIndex,
    )

    suspend fun deleteByPlaylistId(
        playlistId: Long,
    )
}