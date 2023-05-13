package com.buggily.enemy.domain.playlist

import com.buggily.enemy.data.playlist.PlaylistRepositable

class DeletePlaylistById(
    private val repository: PlaylistRepositable,
) {

    suspend operator fun invoke(
        id: Long,
    ) = repository.deleteById(
        id = id,
    )
}
