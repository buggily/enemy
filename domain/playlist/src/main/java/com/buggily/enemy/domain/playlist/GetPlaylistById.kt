package com.buggily.enemy.domain.playlist

import com.buggily.enemy.core.model.playlist.Playlist
import com.buggily.enemy.data.playlist.repository.PlaylistRepositable

class GetPlaylistById(
    private val repository: PlaylistRepositable,
) {

    suspend operator fun invoke(
        id: Long
    ): Playlist = repository.getById(
        id = id,
    )
}
