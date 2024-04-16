package com.buggily.enemy.domain.playlist

import com.buggily.enemy.data.playlist.Playlist
import com.buggily.enemy.data.playlist.PlaylistRepositable

class UpdatePlaylist(
    private val playlistRepository: PlaylistRepositable,
) {

    suspend operator fun invoke(
        playlist: Playlist,
    ) = playlistRepository.update(
        playlist = playlist,
    )
}