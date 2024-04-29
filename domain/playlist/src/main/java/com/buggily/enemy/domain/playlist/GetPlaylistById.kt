package com.buggily.enemy.domain.playlist

import com.buggily.enemy.core.domain.GetInstantText
import com.buggily.enemy.data.playlist.PlaylistRepositable

class GetPlaylistById(
    private val playlistRepository: PlaylistRepositable,
    private val getInstantText: GetInstantText,
) {

    suspend operator fun invoke(
        id: Long
    ): PlaylistUi? = playlistRepository.getById(
        id = id,
    )?.toUi(
        getInstantText = getInstantText,
    )
}
