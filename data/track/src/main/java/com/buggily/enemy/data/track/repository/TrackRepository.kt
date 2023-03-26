package com.buggily.enemy.data.track.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.buggily.core.domain.GetUiDuration
import com.buggily.enemy.core.model.track.Track
import com.buggily.enemy.data.track.ext.map
import com.buggily.enemy.data.track.source.TrackExternalSourceable
import com.buggily.enemy.data.track.source.TrackLocalSourceable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.buggily.enemy.external.track.Track as ExternalTrack
import com.buggily.enemy.local.track.Track as LocalTrack

class TrackRepository(
    private val localSource: TrackLocalSourceable,
    private val externalSource: TrackExternalSourceable,
    private val getUiDuration: GetUiDuration,
) : TrackRepositable {

    override fun getPaging(
        search: String,
    ): Flow<PagingData<Track>> = externalSource.getPaging(
        search = search,
    ).map {  pagingData: PagingData<ExternalTrack> ->
        pagingData.map { it.map(getUiDuration) }
    }

    override fun getPagingByAlbumId(
        albumId: Long,
    ): Flow<PagingData<Track>> = externalSource.getPagingByAlbumId(
        albumId = albumId,
    ).map { pagingData: PagingData<ExternalTrack> ->
        pagingData.map { it.map(getUiDuration) }
    }

    override fun getPagingByPlaylistId(
        playlistId: Long,
    ): Flow<PagingData<Track>> = localSource.getPagingByPlaylistId(
        playlistId = playlistId,
    ).map { pagingData: PagingData<LocalTrack> ->
        pagingData.map { externalSource.getById(it.id).map(getUiDuration) }
    }

    override suspend fun getById(
        id: Long,
    ): Track = externalSource.getById(
        id = id,
    ).map(getUiDuration)

    override suspend fun getByAlbumId(
        albumId: Long,
    ): List<Track> = externalSource.getByAlbumId(albumId).map {
        it.map(getUiDuration)
    }

    override suspend fun getByPlaylistId(
        playlistId: Long,
    ): List<Track> = localSource.getByPlaylistId(playlistId).map {
        externalSource.getById(it.id).map(getUiDuration)
    }
}
