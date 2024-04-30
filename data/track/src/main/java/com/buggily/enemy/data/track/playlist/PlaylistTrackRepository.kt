package com.buggily.enemy.data.track.playlist

import androidx.paging.PagingData
import androidx.paging.map
import com.buggily.enemy.data.track.TrackWithIndex
import com.buggily.enemy.external.track.ExternalTrackSourceable
import com.buggily.enemy.local.playlist.track.LocalPlaylistTrack
import com.buggily.enemy.local.playlist.track.LocalPlaylistTrackSourceable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class PlaylistTrackRepository(
    private val localPlaylistTrackSource: LocalPlaylistTrackSourceable,
    private val externalTrackSource: ExternalTrackSourceable,
) : PlaylistTrackRepositable {

    override fun getPagingByPlaylistId(
        playlistId: Long,
    ): Flow<PagingData<TrackWithIndex>> = localPlaylistTrackSource.getPagingByPlaylistId(
        playlistId = playlistId,
    ).map { pagingData: PagingData<LocalPlaylistTrack> ->
        pagingData.map {
            externalTrackSource.getById(it.id).toWithIndex(
                id = it.id,
                index = it.index,
            )
        }
    }

    override suspend fun getByPlaylistId(
        playlistId: Long,
    ): List<TrackWithIndex> = localPlaylistTrackSource.getByPlaylistId(
        playlistId = playlistId,
    ).mapNotNull { externalTrackSource.getById(it.id)?.toWithIndex(it.index) }

    override suspend fun getByPlaylistIdAndIndex(
        playlistId: Long,
        index: Int,
    ): TrackWithIndex? = localPlaylistTrackSource.getByPlaylistIdAndIndex(
        playlistId = playlistId,
        index = index,
    )?.let { externalTrackSource.getById(it.id)?.toWithIndex(it.index) }

    override suspend fun insertByIdAndPlaylistId(
        id: Long,
        playlistId: Long,
    ) {
        val index: Int = localPlaylistTrackSource.getMaxIndexByPlaylistId(
            playlistId = playlistId,
        )?.inc() ?: TrackWithIndex.DEFAULT_INDEX

        externalTrackSource.getById(id)?.toLocal(
            playlistId = playlistId,
            index = index,
        )?.let { localPlaylistTrackSource.insert(it) }
    }

    override suspend fun deleteByIdAndPlaylistIdAndIndex(
        id: Long,
        playlistId: Long,
        index: Int,
    ) = localPlaylistTrackSource.deleteByIdAndPlaylistIdAndIndex(
        id = id,
        playlistId = playlistId,
        index = index,
    )

    override suspend fun deleteByPlaylistId(
        playlistId: Long,
    ) = localPlaylistTrackSource.deleteByPlaylistId(
        playlistId = playlistId,
    )
}