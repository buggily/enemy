package com.buggily.enemy.domain.playlist

import com.buggily.enemy.data.playlist.PlaylistRepositable

class CreatePlaylist(
    private val playlistRepository: PlaylistRepositable,
) {

    suspend operator fun invoke(
        name: String,
    ) = playlistRepository.create(
        name = name,
    )
}
