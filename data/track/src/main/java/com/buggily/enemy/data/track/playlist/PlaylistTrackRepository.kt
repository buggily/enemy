package com.buggily.enemy.data.track.playlist

import androidx.paging.PagingData
import androidx.paging.map
import com.buggily.enemy.core.domain.GetDurationWithMetadata
import com.buggily.enemy.data.track.Track
import com.buggily.enemy.data.track.TrackWithIndex
import com.buggily.enemy.external.track.ExternalTrackSourceable
import com.buggily.enemy.local.playlist.track.LocalPlaylistTrack
import com.buggily.enemy.local.playlist.track.LocalPlaylistTrackSourceable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class PlaylistTrackRepository(
    private val localPlaylistTrackSource: LocalPlaylistTrackSourceable,
    private val externalTrackSource: ExternalTrackSourceable,
    private val getDurationWithMetadata: GetDurationWithMetadata,
) : PlaylistTrackRepositable {

    override fun getPagingByPlaylistId(
        playlistId: Long,
    ): Flow<PagingData<TrackWithIndex>> = localPlaylistTrackSource.getPagingByPlaylistId(
        playlistId = playlistId,
    ).map { pagingData: PagingData<LocalPlaylistTrack> ->
        pagingData.map {
            checkNotNull(externalTrackSource.getById(it.id)).toWithIndex(
                index = it.index,
                getDurationWithMetadata = getDurationWithMetadata,
            )
        }
    }

    override suspend fun getByPlaylistId(
        playlistId: Long,
    ): List<TrackWithIndex> = localPlaylistTrackSource.getByPlaylistId(playlistId).map {
        checkNotNull(externalTrackSource.getById(it.id)).toWithIndex(
            index = it.index,
            getDurationWithMetadata = getDurationWithMetadata,
        )
    }

    override suspend fun getByPlaylistIdAndIndex(
        playlistId: Long,
        index: Int,
    ): TrackWithIndex? = localPlaylistTrackSource.getByPlaylistIdAndIndex(
        playlistId = playlistId,
        index = index,
    )?.let {
        externalTrackSource.getById(it.id)?.toWithIndex(
            index = it.index,
            getDurationWithMetadata = getDurationWithMetadata,
        )
    }

    override suspend fun insertByPlaylistId(
        playlistId: Long,
        track: Track,
    ) {
        val index: Int = localPlaylistTrackSource.getMaxIndexByPlaylistId(
            playlistId = playlistId,
        )?.inc() ?: 0

        return track.toLocal(
            playlistId = playlistId,
            index = index,
        ).let { localPlaylistTrackSource.insert(it) }
    }

    override suspend fun deleteByPlaylistId(
        playlistId: Long,
        track: TrackWithIndex,
    ) = track.toLocal(
        playlistId = playlistId,
    ).let { localPlaylistTrackSource.delete(it) }

    override suspend fun deleteByPlaylistId(
        playlistId: Long,
    ) = localPlaylistTrackSource.deleteByPlaylistId(
        playlistId = playlistId,
    )
}