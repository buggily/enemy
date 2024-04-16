package com.buggily.enemy.data.playlist

import androidx.paging.PagingData
import androidx.paging.map
import com.buggily.enemy.core.data.InstantWithMetadata
import com.buggily.enemy.core.domain.GetInstantWithMetadata
import com.buggily.enemy.core.domain.GetInstantWithMetadataFromInstant
import com.buggily.enemy.local.playlist.LocalPlaylist
import com.buggily.enemy.local.playlist.LocalPlaylistSourceable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class PlaylistRepository(
    private val localPlaylistSource: LocalPlaylistSourceable,
    private val getInstantWithMetadata: GetInstantWithMetadata,
    private val getInstantWithMetadataFromInstant: GetInstantWithMetadataFromInstant,
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

    override suspend fun deleteById(
        id: Long,
    ) = localPlaylistSource.deleteById(
        id = id,
    )

    override suspend fun create(
        name: String,
    ) {
        val instant: InstantWithMetadata = getInstantWithMetadata()

        val playlist: LocalPlaylist = Playlist(
            name = name,
            creationInstant = instant,
            modificationInstant = instant,
        ).toLocal()

        localPlaylistSource.insert(playlist)
    }

    override suspend fun update(
        playlist: Playlist,
    ) {
        playlist.copy(
            modificationInstant = getInstantWithMetadata(),
        ).let { localPlaylistSource.update(it.toLocal()) }
    }
}
