package com.buggily.enemy.domain.playlist

import com.buggily.enemy.core.model.playlist.Playlist
import com.buggily.enemy.data.playlist.repository.PlaylistRepositable

class UpdatePlaylist(
    private val repository: PlaylistRepositable,
) {

    suspend operator fun invoke(
        playlist: Playlist,
    ) = repository.update(
        playlist = playlist,
    )
}
