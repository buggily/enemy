package com.buggily.enemy.data.playlist.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.buggily.core.domain.GetUiInstantFromInstant
import com.buggily.enemy.core.model.playlist.Playlist
import com.buggily.enemy.data.playlist.ext.map
import com.buggily.enemy.data.playlist.source.PlaylistSourceable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlaylistRepository(
    private val source: PlaylistSourceable,
    private val getUiInstantFromInstant: GetUiInstantFromInstant,
) : PlaylistRepositable {

    override fun getPaging(
        search: String,
    ): Flow<PagingData<Playlist>> = source.getPaging(
        search = search,
    ).map { it.map { playlist -> playlist.map(getUiInstantFromInstant) } }

    override suspend fun getById(
        id: Long,
    ): Playlist = source.getById(
        id = id,
    ).map(getUiInstantFromInstant)

    override suspend fun insert(
        playlist: Playlist,
    ) = playlist.map().let {
        source.insert(it)
    }

    override suspend fun update(
        playlist: Playlist,
    ) = playlist.map().let {
        source.update(it)
    }

    override suspend fun delete(
        playlist: Playlist,
    ) = playlist.map().let {
        source.delete(it)
    }
}
