package com.buggily.enemy.local.playlist.track

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

internal class LocalPlaylistTrackSource(
    private val config: PagingConfig,
    private val localPlaylistTrackDao: LocalPlaylistTrackDao,
) : LocalPlaylistTrackSourceable {

    override fun getPagingByPlaylistId(
        playlistId: Long,
    ): Flow<PagingData<LocalPlaylistTrack>> = Pager(
        config = config,
        pagingSourceFactory = { localPlaylistTrackDao.getPagingByPlaylistId(playlistId) },
    ).flow

    override suspend fun getByPlaylistId(
        playlistId: Long,
    ): List<LocalPlaylistTrack> = localPlaylistTrackDao.getByPlaylistId(
        playlistId = playlistId,
    )

    override suspend fun getMaxIndexByPlaylistId(
        playlistId: Long,
    ): Int? = localPlaylistTrackDao.getMaxIndexByPlaylistId(
        playlistId = playlistId,
    )

    override suspend fun getByPlaylistIdAndIndex(
        playlistId: Long,
        index: Int,
    ): LocalPlaylistTrack? = localPlaylistTrackDao.getByPlaylistIdAndIndex(
        playlistId = playlistId,
        index = index,
    )

    override suspend fun insert(
        track: LocalPlaylistTrack,
    ) = localPlaylistTrackDao.insert(
        track = track,
    )

    override suspend fun delete(
        track: LocalPlaylistTrack,
    ) = localPlaylistTrackDao.delete(
        track = track,
    )

    override suspend fun deleteByPlaylistId(
        playlistId: Long,
    ) = localPlaylistTrackDao.deleteByPlaylistId(
        playlistId = playlistId,
    )
}
