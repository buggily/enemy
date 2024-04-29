package com.buggily.enemy.domain.playlist

import com.buggily.enemy.data.playlist.PlaylistRepositable

class UpdatePlaylistById(
    private val playlistRepository: PlaylistRepositable,
) {

    suspend operator fun invoke(
        id: Long,
        name: String,
    ) = playlistRepository.updateById(
        id = id,
        name = name,
    )
}
