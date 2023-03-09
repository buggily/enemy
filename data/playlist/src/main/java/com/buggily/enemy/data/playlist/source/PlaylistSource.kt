package com.buggily.enemy.data.playlist.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.buggily.enemy.local.playlist.Playlist
import com.buggily.enemy.local.playlist.PlaylistDao
import kotlinx.coroutines.flow.Flow

class PlaylistSource(
    private val config: PagingConfig,
    private val dao: PlaylistDao,
) : PlaylistSourceable {

    override fun getPaging(
        search: String,
    ): Flow<PagingData<Playlist>> = Pager(
        config = config,
        pagingSourceFactory = {
            dao.getPaging(
                search = "%$search%",
            )
        },
    ).flow

    override suspend fun getById(
        id: Long,
    ): Playlist = dao.getById(
        id = id,
    )

    override suspend fun insert(
        playlist: Playlist,
    ) = dao.insert(
        playlist = playlist,
    )

    override suspend fun update(
        playlist: Playlist,
    ) = dao.update(
        playlist = playlist,
    )

    override suspend fun delete(
        playlist: Playlist,
    ) = dao.delete(
        playlist = playlist,
    )
}
