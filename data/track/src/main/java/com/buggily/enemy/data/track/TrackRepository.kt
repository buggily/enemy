package com.buggily.enemy.data.track

import androidx.paging.PagingData
import androidx.paging.map
import com.buggily.core.domain.GetDurationWithMetadata
import com.buggily.enemy.external.track.ExternalTrack
import com.buggily.enemy.external.track.ExternalTrackSourceable
import com.buggily.enemy.local.track.LocalTrack
import com.buggily.enemy.local.track.LocalTrackSourceable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class TrackRepository(
    private val getDurationWithMetadata: GetDurationWithMetadata,
    private val localTrackSource: LocalTrackSourceable,
    private val externalTrackSource: ExternalTrackSourceable,
) : TrackRepositable {

    override fun getPaging(
        search: String,
    ): Flow<PagingData<Track>> = externalTrackSource.getPaging(
        search = search,
    ).map { pagingData: PagingData<ExternalTrack> ->
        pagingData.map { it.to(getDurationWithMetadata) }
    }

    override fun getPagingByAlbumId(
        albumId: Long,
    ): Flow<PagingData<Track>> = externalTrackSource.getPagingByAlbumId(
        albumId = albumId,
    ).map { pagingData: PagingData<ExternalTrack> ->
        pagingData.map { it.to(getDurationWithMetadata) }
    }

    override fun getPagingByPlaylistId(
        playlistId: Long,
    ): Flow<PagingData<TrackWithIndex>> = localTrackSource.getPagingByPlaylistId(
        playlistId = playlistId,
    ).map { pagingData: PagingData<LocalTrack> ->
        pagingData.map {
            checkNotNull(externalTrackSource.getById(it.id)).toWithIndex(
                index = it.index,
                getDurationWithMetadata = getDurationWithMetadata,
            )
        }
    }

    override suspend fun getById(
        id: Long,
    ): Track? = externalTrackSource.getById(
        id = id,
    )?.to(getDurationWithMetadata)

    override suspend fun getByAlbumId(
        albumId: Long,
    ): List<Track> = externalTrackSource.getByAlbumId(albumId).map {
        it.to(getDurationWithMetadata)
    }

    override suspend fun getByPlaylistId(
        playlistId: Long,
    ): List<TrackWithIndex> = localTrackSource.getByPlaylistId(playlistId).map {
        checkNotNull(externalTrackSource.getById(it.id)).toWithIndex(
            index = it.index,
            getDurationWithMetadata = getDurationWithMetadata,
        )
    }

    override suspend fun getByPlaylistIdAndIndex(
        playlistId: Long,
        index: Int,
    ): TrackWithIndex? = localTrackSource.getByPlaylistIdAndIndex(
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
    ) = track.toLocal(
        playlistId = playlistId,
        index = localTrackSource.getMaxIndexByPlaylistId(playlistId)?.inc() ?: 0,
    ).let { localTrackSource.insert(it) }

    override suspend fun deleteByPlaylistId(
        playlistId: Long,
        track: TrackWithIndex,
    ) = track.toLocal(
        playlistId = playlistId,
    ).let { localTrackSource.delete(it) }

    override suspend fun deleteByPlaylistId(
        playlistId: Long,
    ) = localTrackSource.deleteByPlaylistId(
        playlistId = playlistId,
    )
}
