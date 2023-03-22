package com.buggily.enemy.data.track.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.buggily.core.domain.GetUiDuration
import com.buggily.enemy.core.model.track.Track
import com.buggily.enemy.data.track.ext.paging.map
import com.buggily.enemy.data.track.source.TrackSourceable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TrackRepository(
    private val source: TrackSourceable,
    private val getUiDuration: GetUiDuration,
) : TrackRepositable {

    override fun getPaging(
        search: String,
    ): Flow<PagingData<Track>>  = source.getPaging(
        search = search,
    ).map { it.map { track -> track.map(getUiDuration) } }

    override fun getPagingByAlbumId(
        albumId: Long,
    ): Flow<PagingData<Track>> = source.getPagingByAlbumId(albumId).map {
        it.map { track -> track.map(getUiDuration) }
    }

    override fun getPagingByPlaylistId(
        playlistId: Long,
    ): Flow<PagingData<Track>> = source.getPagingByPlaylistId(playlistId).map {
        it.map { track -> track.map(getUiDuration) }
    }

    override suspend fun getById(
        id: Long,
    ): Track = source.getById(
        id = id,
    ).map(getUiDuration)

    override suspend fun getByAlbumId(
        albumId: Long,
    ): List<Track> = source.getByAlbumId(albumId).map {
        it.map(getUiDuration)
    }

    override suspend fun getByPlaylistId(
        playlistId: Long,
    ): List<Track> = source.getByPlaylistId(playlistId).map {
        it.map(getUiDuration)
    }
}
