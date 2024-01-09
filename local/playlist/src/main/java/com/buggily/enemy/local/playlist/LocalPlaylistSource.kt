package com.buggily.enemy.local.playlist

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

internal class LocalPlaylistSource(
    private val config: PagingConfig,
    private val localPlaylistDao: LocalPlaylistDao,
) : LocalPlaylistSourceable {

    override fun getPaging(
        search: String,
    ): Flow<PagingData<LocalPlaylist>> = Pager(
        config = config,
        pagingSourceFactory = { localPlaylistDao.getPaging("%$search%") }
    ).flow

    override suspend fun getById(
        id: Long,
    ): LocalPlaylist? = localPlaylistDao.getById(
        id = id,
    )

    override suspend fun deleteById(
        id: Long,
    ) = localPlaylistDao.deleteById(
        id = id,
    )

    override suspend fun insert(
        playlist: LocalPlaylist,
    ) = localPlaylistDao.insert(
        playlist = playlist,
    )
}
