package com.buggily.enemy.domain.playlistWithTracks

import com.buggily.enemy.data.playlistWithTracks.PlaylistWithTracksRepositable

class DeletePlaylistById(
    private val playlistWithTracksRepository: PlaylistWithTracksRepositable,
) {

    suspend operator fun invoke(
        id: Long,
    ) = playlistWithTracksRepository.deletePlaylistByPlaylistId(
        playlistId = id,
    )
}
