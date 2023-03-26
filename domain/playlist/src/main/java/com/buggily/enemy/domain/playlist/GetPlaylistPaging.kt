package com.buggily.enemy.domain.playlist

import androidx.paging.PagingData
import com.buggily.enemy.core.model.playlist.Playlist
import com.buggily.enemy.data.playlist.repository.PlaylistRepositable
import kotlinx.coroutines.flow.Flow

class GetPlaylistPaging(
    private val repository: PlaylistRepositable,
) {

    operator fun invoke(
        search: String,
    ): Flow<PagingData<Playlist>> = repository.getPaging(
        search = search,
    )
}