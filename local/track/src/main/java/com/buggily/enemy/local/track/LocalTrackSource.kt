package com.buggily.enemy.local.track

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

internal class LocalTrackSource(
    private val config: PagingConfig,
    private val localTrackDao: LocalTrackDao,
) : LocalTrackSourceable {

    override fun getPagingByPlaylistId(
        playlistId: Long,
    ): Flow<PagingData<LocalTrack>> = Pager(
        config = config,
        pagingSourceFactory = { localTrackDao.getPagingByPlaylistId(playlistId) },
    ).flow

    override suspend fun getByPlaylistId(
        playlistId: Long,
    ): List<LocalTrack> = localTrackDao.getByPlaylistId(
        playlistId = playlistId,
    )

    override suspend fun getMaxIndexByPlaylistId(
        playlistId: Long,
    ): Int? = localTrackDao.getMaxIndexByPlaylistId(
        playlistId = playlistId,
    )

    override suspend fun getByPlaylistIdAndIndex(
        playlistId: Long,
        index: Int,
    ): LocalTrack? = localTrackDao.getByPlaylistIdAndIndex(
        playlistId = playlistId,
        index = index,
    )

    override suspend fun insert(
        track: LocalTrack,
    ) = localTrackDao.insert(
        track = track,
    )

    override suspend fun delete(
        track: LocalTrack,
    ) = localTrackDao.delete(
        track = track,
    )

    override suspend fun deleteByPlaylistId(
        playlistId: Long,
    ) = localTrackDao.deleteByPlaylistId(
        playlistId = playlistId,
    )
}
