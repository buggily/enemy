package com.buggily.enemy.data.track.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.buggily.enemy.local.track.Track
import com.buggily.enemy.local.track.TrackDao
import kotlinx.coroutines.flow.Flow

class TrackLocalSource(
    private val config: PagingConfig,
    private val dao: TrackDao,
) : TrackLocalSourceable {

    override fun getPagingByPlaylistId(
        playlistId: Long,
    ): Flow<PagingData<Track>> = Pager(
        config = config,
        pagingSourceFactory = { dao.getPagingByPlaylistId(playlistId) },
    ).flow

    override suspend fun getByPlaylistId(
        playlistId: Long,
    ): List<Track> = dao.getByPlaylistId(
        playlistId = playlistId,
    )
}
