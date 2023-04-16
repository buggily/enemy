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
    ): Flow<PagingData<Track>> = localTrackSource.getPagingByPlaylistId(
        playlistId = playlistId,
    ).map { pagingData: PagingData<LocalTrack> ->
        pagingData.map { externalTrackSource.getById(it.id).to(getDurationWithMetadata) }
    }

    override suspend fun getById(
        id: Long,
    ): Track = externalTrackSource.getById(
        id = id,
    ).to(getDurationWithMetadata)

    override suspend fun getByAlbumId(
        albumId: Long,
    ): List<Track> = externalTrackSource.getByAlbumId(albumId).map {
        it.to(getDurationWithMetadata)
    }

    override suspend fun getByPlaylistId(
        playlistId: Long,
    ): List<Track> = localTrackSource.getByPlaylistId(playlistId).map {
        externalTrackSource.getById(it.id).to(getDurationWithMetadata)
    }
}
