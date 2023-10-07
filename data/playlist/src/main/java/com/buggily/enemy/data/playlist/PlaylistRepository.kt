package com.buggily.enemy.data.playlist

import androidx.paging.PagingData
import androidx.paging.map
import com.buggily.enemy.core.domain.GetInstantWithMetadataFromInstant
import com.buggily.enemy.local.playlist.LocalPlaylist
import com.buggily.enemy.local.playlist.LocalPlaylistSourceable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class PlaylistRepository(
    private val getInstantWithMetadataFromInstant: GetInstantWithMetadataFromInstant,
    private val localPlaylistSource: LocalPlaylistSourceable,
) : PlaylistRepositable {

    override fun getPaging(
        search: String,
    ): Flow<PagingData<Playlist>> = localPlaylistSource.getPaging(
        search = search,
    ).map { pagingData: PagingData<LocalPlaylist> ->
        pagingData.map { it.to(getInstantWithMetadataFromInstant) }
    }

    override suspend fun getById(
        id: Long,
    ): Playlist? = localPlaylistSource.getById(
        id = id,
    )?.to(getInstantWithMetadataFromInstant)

    override suspend fun insert(
        playlist: Playlist,
    ) = playlist.toLocal().let {
        localPlaylistSource.insert(it)
    }

    override suspend fun update(
        playlist: Playlist,
    ) = playlist.toLocal().let {
        localPlaylistSource.update(it)
    }

    override suspend fun deleteById(
        id: Long,
    ) = localPlaylistSource.deleteById(
        id = id,
    )
}
