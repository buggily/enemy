package com.buggily.enemy.domain.playlist

import com.buggily.enemy.data.playlist.Playlist
import com.buggily.enemy.data.playlist.PlaylistRepositable

class GetPlaylistById(
    private val repository: PlaylistRepositable,
) {

    suspend operator fun invoke(
        id: Long
    ): Playlist = repository.getById(
        id = id,
    )
}
