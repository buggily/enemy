package com.buggily.enemy.domain.playlist

import com.buggily.enemy.core.model.playlist.Playlist
import com.buggily.enemy.data.playlist.repository.PlaylistRepositable

class DeletePlaylist(
    private val repository: PlaylistRepositable,
) {

    suspend fun invoke(
        playlist: Playlist,
    ) = repository.delete(
        playlist = playlist,
    )
}
