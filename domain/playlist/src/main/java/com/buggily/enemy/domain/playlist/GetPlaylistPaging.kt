package com.buggily.enemy.domain.playlist

import androidx.paging.PagingData
import androidx.paging.map
import com.buggily.enemy.core.domain.GetInstantText
import com.buggily.enemy.data.playlist.Playlist
import com.buggily.enemy.data.playlist.PlaylistRepositable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPlaylistPaging(
    private val playlistRepository: PlaylistRepositable,
    private val getInstantText: GetInstantText,
) {

    operator fun invoke(
        search: String,
    ): Flow<PagingData<PlaylistUi>> = playlistRepository.getPaging(search).map {
        it.map { playlist: Playlist -> playlist.toUi(getInstantText) }
    }
}
