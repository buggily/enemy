package com.buggily.enemy.local.track

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class LocalTrackSource(
    private val config: PagingConfig,
    private val localTrackDao: LocalTrackDao,
) : LocalTrackSourceable {

    override fun getPagingByRecency(): Flow<PagingData<LocalTrack>> = Pager(
        config = config,
        pagingSourceFactory = { localTrackDao.getPagingByRecency() },
    ).flow

    override fun getPagingByPopularity(): Flow<PagingData<LocalTrack>> = Pager(
        config = config,
        pagingSourceFactory = { localTrackDao.getPagingByPopularity() },
    ).flow

    override suspend fun getById(
        id: Long,
    ): LocalTrack? = localTrackDao.getById(
        id = id,
    )

    override suspend fun insert(
        track: LocalTrack,
    ) = localTrackDao.insert(
        track = track,
    )

    override suspend fun update(
        track: LocalTrack,
    ) = localTrackDao.update(
        track = track,
    )

    override suspend fun delete() = localTrackDao.delete()
}
