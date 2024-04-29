package com.buggily.enemy.data.playlist

import androidx.paging.PagingData
import androidx.paging.map
import com.buggily.enemy.core.domain.GetInstant
import com.buggily.enemy.local.playlist.LocalPlaylist
import com.buggily.enemy.local.playlist.LocalPlaylistSourceable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant

internal class PlaylistRepository(
    private val localPlaylistSource: LocalPlaylistSourceable,
    private val getInstant: GetInstant,
) : PlaylistRepositable {

    override fun getPaging(
        search: String,
    ): Flow<PagingData<Playlist>> = localPlaylistSource.getPaging(
        search = search,
    ).map { pagingData: PagingData<LocalPlaylist> ->
        pagingData.map { it.to() }
    }

    override suspend fun getById(
        id: Long,
    ): Playlist? = localPlaylistSource.getById(
        id = id,
    )?.to()

    override suspend fun deleteById(
        id: Long,
    ) = localPlaylistSource.deleteById(
        id = id,
    )

    override suspend fun create(
        name: String,
    ) {
        val instant: Instant = getInstant()
        val playlist: LocalPlaylist = Playlist(
            name = name,
            creationInstant = instant,
            modificationInstant = instant,
        ).toLocal()

        localPlaylistSource.insert(playlist)
    }

    override suspend fun updateById(
        id: Long,
    ) {
        localPlaylistSource.getById(id)?.copy(
            modificationInstant = getInstant(),
        )?.let { localPlaylistSource.update(it) }
    }

    override suspend fun updateById(
        id: Long,
        name: String,
    ) {
        localPlaylistSource.getById(id)?.copy(
            name = name,
            modificationInstant = getInstant(),
        )?.let { localPlaylistSource.update(it) }
    }
}
