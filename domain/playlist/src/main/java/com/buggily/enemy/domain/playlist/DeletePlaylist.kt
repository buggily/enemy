package com.buggily.enemy.domain.playlist

import com.buggily.enemy.data.playlist.Playlist
import com.buggily.enemy.data.playlist.PlaylistRepositable

class DeletePlaylist(
    private val repository: PlaylistRepositable,
) {

    suspend fun invoke(
        playlist: Playlist,
    ) = repository.delete(
        playlist = playlist,
    )
}
